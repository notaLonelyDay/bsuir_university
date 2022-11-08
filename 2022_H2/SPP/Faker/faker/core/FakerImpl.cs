using System.Reflection;
using faker.core.util;
using Faker.gen;

namespace faker.core;

public class FakerImpl : Faker {
    public List<Generator> generators { private set; get; }

    private Stack<Type> dependentTypes = new Stack<Type>();

    public T Create<T>() {
        generators = createGenerators();

        return (T)Create(typeof(T));
    }

    /**
     * First try to generate from generator
     * Then try to generate from constructor
     * Then if generated fill fields and properties
     */
    public object Create(Type t) {
        throwIfCircularDependency(t, dependentTypes);


        object? result;
        dependentTypes.Push(t);

        try {
            result = generateFromGenerator(t);
            if (result == null) {
                result = generateFromConstructor(t);
                if (result != null) {
                    initProperties(result);
                    initFields(result);
                }
            }
        }
        finally {
            dependentTypes.Pop();
        }


        return result;
    }

    private void throwIfCircularDependency(Type t, Stack<Type> dependentTypes) {
        foreach (var dependentType in dependentTypes) {
            if (dependentType == t) {
                throw new CircularDependencyException();
            }
        }
    }

    /**
     * If have generator -> use it
     * If can't generate by generator -> null
     */
    private object? generateFromGenerator(Type t) {
        foreach (var gen in generators) {
            if (gen.canGenerate(t)) {
                return gen.generate(t);
            }
        }

        return null;
    }

    public static object? getDefaultValue(Type t) {
        return t.IsValueType ? Activator.CreateInstance(t) : null;
    }


    /**
     * Always returns somethings, maybe null
     */
    private object? generateFromConstructor(Type t) {
        var constructors = t
            .GetConstructors()
            .OrderByDescending(
                it => it.GetParameters().Length
            );

        object? result;
        foreach (var constructor in constructors) {
            try {
                var parameters = constructor.GetParameters();
                var initializedParameters = new List<object?>();
                foreach (var parameter in parameters) {
                    initializedParameters.Add(Create(parameter.ParameterType));
                }

                return constructor.Invoke(initializedParameters.ToArray());
            }
            catch {
                // do nothing
            }
        }

        var defaultValue = getDefaultValue(t);
        if (defaultValue != null) {
            return defaultValue;
        }
        else if (Nullable.GetUnderlyingType(t) != null) {
            // is nullable
            return null;
        }

        throw new CantGenerateByConstructorException();
    }

    private void initFields(object obj) {
        var type = obj.GetType();
        var fields = type.GetFields();
        foreach (var field in fields) {
            if (field.IsPublic) {
                var generated = Create(field.FieldType);
                field.SetValue(obj, generated);
            }
        }
    }

    private void initProperties(object obj) {
        var type = obj.GetType();
        var props = type.GetProperties();
        foreach (var prop in props) {
            if (prop.CanWrite && prop.GetSetMethod()!.IsPublic) {
                var generated = Create(prop.PropertyType);
                prop.SetValue(obj, generated);
            }
        }
    }


    private List<Generator> createGenerators() {
        var sharedRandom = new Random();
        return new List<Generator>(
        ) {
            new BooleanGenerator(sharedRandom),
            new ByteGenerator(sharedRandom),
            new CharGenerator(sharedRandom),
            new DateTimeGenerator(sharedRandom),
            new DecimalGenerator(sharedRandom),
            new DoubleGenerator(sharedRandom),
            new FloatGenerator(sharedRandom),
            new IntegerGenerator(sharedRandom),
            new ListGenerator(Create),
            new LongGenerator(sharedRandom),
            new ShortGenerator(sharedRandom),
            new SignedByteGenerator(sharedRandom),
            new StringGenerator(sharedRandom),
        };
    }
}