using Faker.gen.util;

namespace Faker.gen;

public class CharGenerator : Generator {
    private readonly Random random;

    public bool canGenerate(Type t) {
        return t == typeof(char);
    }

    public CharGenerator(Random? rand = null) {
        random = rand ?? new Random();
    }

    public object generate(Type t) {
        this.throwIfCantGenerate(t);
        int num = random.Next(0, 26);
        char let = (char)('a' + num);
        return let;
    }
}