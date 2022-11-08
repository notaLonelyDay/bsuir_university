using Faker.gen.util;

namespace Faker.gen;

public class BooleanGenerator : Generator {
    private readonly Random random;

    public bool canGenerate(Type t) {
        return t == typeof(bool);
    }


    public BooleanGenerator(Random? rand = null) {
        random = rand ?? new Random();
    }

    public object generate(Type t) {
        this.throwIfCantGenerate(t);
        return Convert.ToBoolean(random.NextInt64(0, 2));
    }
}