namespace Faker.gen;

public interface Generator
{
    public bool canGenerate(Type t);
    public object generate(Type t);
}