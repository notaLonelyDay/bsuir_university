using Faker.gen.util;

namespace Faker.gen;

public class FloatGenerator : Generator {
    private readonly Random random;

    public bool canGenerate(Type t) {
        return t == typeof(float);
    }

    public FloatGenerator(Random? rand = null) {
        random = rand ?? new Random();
    }

    public object generate(Type t) {
        this.throwIfCantGenerate(t);
        return (float)(random.Next() + random.NextDouble());
    }
}