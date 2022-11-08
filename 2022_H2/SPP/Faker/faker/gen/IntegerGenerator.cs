using Faker.gen.util;

namespace Faker.gen;

public class IntegerGenerator : Generator {
    private readonly Random random;

    public bool canGenerate(Type t) {
        return t == typeof(int);
    }


    public IntegerGenerator(Random? rand = null) {
        random = rand ?? new Random();
    }

    public object generate(Type t) {
        this.throwIfCantGenerate(t);
        return random.Next();
    }
}