using Faker.gen.util;

namespace Faker.gen;

public class ByteGenerator: Generator {
    private readonly Random random;
    public bool canGenerate(Type t) {
            return t == typeof(byte);
    }

    public ByteGenerator(Random? rand = null) {
        random = rand ?? new Random();
    }

    public object generate(Type t) {
        this.throwIfCantGenerate(t);
        return (byte)random.Next(256);
    }
}