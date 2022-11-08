using System.Reflection;
using Faker.gen.util;

namespace Faker.gen;

public class ListGenerator : Generator {
    private readonly Func<Type, object?> generateFunction;

    public ListGenerator(Func<Type, object?> generateFunction) {
        this.generateFunction = generateFunction;
    }

    public bool canGenerate(Type t) {
        if (t.IsGenericType) {
            return t.GetGenericTypeDefinition() == typeof(List<>);
        }

        return false;
    }

    public object generate(Type t) {
        this.throwIfCantGenerate(t);
        var genericArg = t.GetGenericArguments().First();
        var parametrisedList = typeof(List<>).MakeGenericType(genericArg);
        var list = Activator.CreateInstance(parametrisedList)!;
        var addMethod = parametrisedList.GetMethod("Add")!;

        for (int i = 0; i < 10; i++) {
            var result = generateFunction(genericArg);
            addMethod.Invoke(list, new object?[] { result });
        }

        return list;
    }
}