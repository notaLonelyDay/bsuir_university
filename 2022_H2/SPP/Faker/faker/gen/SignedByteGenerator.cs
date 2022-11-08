using Faker.gen.util;

namespace Faker.gen;

public class SignedByteGenerator : Generator {
    private readonly Random random;

    public bool canGenerate(Type t) {
            return t == typeof(sbyte);
    }

    public SignedByteGenerator(Random? rand = null) {
        random = rand ?? new Random();
    }

    public object generate(Type t) {
        this.throwIfCantGenerate(t);
        return (sbyte)random.Next(256);
    }
}