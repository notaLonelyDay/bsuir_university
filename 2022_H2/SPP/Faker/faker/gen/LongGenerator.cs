using Faker.gen.util;

namespace Faker.gen;

public class LongGenerator : Generator {
    private readonly Random random;

    public bool canGenerate(Type t) {
        return t == typeof(long);
    }

    public LongGenerator(Random? rand = null) {
        random = rand ?? new Random();
    }

    public object generate(Type t) {
        this.throwIfCantGenerate(t);
        return random.NextInt64();
    }
}