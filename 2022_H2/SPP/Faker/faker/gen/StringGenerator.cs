using System.Text;
using Faker.gen.util;

namespace Faker.gen;

public class StringGenerator : Generator {
    private readonly Random random;

    private const int minLength = 10;
    private const int maxLength = 32;

    public bool canGenerate(Type t) {
        return t == typeof(string);
    }

    public StringGenerator(Random? rand = null) {
        random = rand ?? new Random();
    }

    public object generate(Type t) {
        this.throwIfCantGenerate(t);
        var len = random.Next(minLength, maxLength);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int num = random.Next(0, 26); // Zero to 25
            char let = (char)('a' + num);
            sb.Append(len);
        }

        return sb.ToString();
    }
}