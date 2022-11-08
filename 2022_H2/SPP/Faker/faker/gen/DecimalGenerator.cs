using Faker.gen.util;

namespace Faker.gen;

public class DecimalGenerator : Generator {
    private readonly Random random;

    public bool canGenerate(Type t) {

            return t == typeof(decimal);
    }

    public DecimalGenerator(Random? rand = null) {
        random = rand ?? new Random();
    }

    public object generate(Type t) {
        this.throwIfCantGenerate(t);
        return (decimal)(random.NextInt64() + random.NextDouble());
    }
}