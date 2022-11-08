namespace Faker.gen.util;

public static class Extensions {
    public static void throwIfCantGenerate(this Generator generator, Type t) {
        if (!generator.canGenerate(t)) {
            throw new NotImplementedException($"{generator.GetType().FullName} cant generate {t.FullName}");
        }
    }
}