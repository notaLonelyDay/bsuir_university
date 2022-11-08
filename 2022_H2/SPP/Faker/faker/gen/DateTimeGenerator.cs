using Faker.gen.util;

namespace Faker.gen;

public class DateTimeGenerator : Generator {
    private readonly Random random;

    public bool canGenerate(Type t) {
        return t == typeof(DateTime);
    }

    public DateTimeGenerator(Random? rand = null) {
        random = rand ?? new Random();
    }

    private const int initYear = 1990;
    private const int initMonth = 1;
    private const int initDay = 1;
    private const int maxDays = 10000;
    private const int maxSeconds = 10000;

    public object generate(Type t) {
        this.throwIfCantGenerate(t);
        DateTime dateTime = new DateTime(initYear, initMonth, initDay);
        var days = random.Next(maxDays);
        var seconds = random.Next(maxSeconds);
        var result = dateTime.AddDays(days).AddSeconds(seconds);
        return result;
    }
}