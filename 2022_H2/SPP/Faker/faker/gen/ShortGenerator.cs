using Faker.gen.util;

namespace Faker.gen;

public class ShortGenerator : Generator {
    private readonly Random random;
    private const int shortMaxValue = 32768;

    public bool canGenerate(Type t) {
            return t == typeof(short);
    }

    public ShortGenerator(Random? rand = null) {
        random = rand ?? new Random();
    }

    public object generate(Type t) {
        this.throwIfCantGenerate(t);
        return (short)random.Next(shortMaxValue);
    }
}