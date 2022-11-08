using Faker.gen.util;

namespace Faker.gen;

public class DoubleGenerator : Generator {
    private readonly Random random;

    public bool canGenerate(Type t) {
        return t == typeof(double);
    }

    public DoubleGenerator(Random? rand = null) {
        random = rand ?? new Random();
    }

    public object generate(Type t) {
        this.throwIfCantGenerate(t);
        return random.Next() + random.NextDouble();
    }
}