using faker.core;
using faker.core.util;
using FluentAssertions;
using Xunit;

namespace Faker.test;

public class FakerTests {
    [Fact]
    public void byteGeneratorShouldReturnNotDefault() {
        var faker = new FakerImpl();
        var result = faker.Create<byte>();

        result.Should().NotBe(default);
    }

    [Fact]
    public void charGeneratorShouldReturnNotDefault() {
        var faker = new FakerImpl();
        var result = faker.Create<char>();

        result.Should().NotBe(default);
    }

    [Fact]
    public void dateTimeGeneratorShouldReturnNotDefault() {
        var faker = new FakerImpl();
        var result = faker.Create<DateTime>();

        result.Should().NotBe(default);
    }

    [Fact]
    public void decimalGeneratorShouldReturnNotDefault() {
        var faker = new FakerImpl();
        var result = faker.Create<decimal>();

        result.Should().NotBe(default);
    }

    [Fact]
    public void doubleGeneratorShouldReturnNotDefault() {
        var faker = new FakerImpl();
        var result = faker.Create<double>();

        result.Should().NotBe(default);
    }

    [Fact]
    public void floatGeneratorShouldReturnNotDefault() {
        var faker = new FakerImpl();
        var result = faker.Create<float>();

        result.Should().NotBe(default);
    }

    [Fact]
    public void integerGeneratorShouldReturnNotDefault() {
        var faker = new FakerImpl();
        var result = faker.Create<int>();

        result.Should().NotBe(default);
    }

    [Fact]
    public void listGeneratorShouldReturnNotDefault() {
        var faker = new FakerImpl();
        var result = faker.Create<List<string>>();

        result.Should().NotBeNullOrEmpty();
    }

    [Fact]
    public void StringGeneratorShouldReturnNotDefault() {
        var faker = new FakerImpl();
        var result = faker.Create<string>();

        result.Should().NotBe(default);
    }

    [Fact]
    public void ShouldFakeUserType() {
        var fakerImpl = new FakerImpl();
        var result = fakerImpl.Create<Class>();
        result.Should().NotBeNull();
        result.FirstName.Should().NotBeNullOrEmpty();
        result.Age.Should().BeGreaterThan(0);
        result.Children.Should().NotBeNullOrEmpty();
    }

    [Fact]
    public void shouldThrowCyclicDependencyException() {
        var fakerImpl = new FakerImpl();
        Action act = () => fakerImpl.Create<ClassWithCyclicDependency>();
        act.Should().Throw<CircularDependencyException>();
    }

    [Fact]
    public void ShouldNotGeneratePrivateSetters() {
        var fakerImpl = new FakerImpl();
        var result = fakerImpl.Create<ClassWithPrivateSetter>();
        result.Should().NotBeNull();
        result.PrivateProperty.Should().Be(default);
    }

    [Fact]
    public void ShouldUseConstructorWithMoreParams() {
        var fakerImpl = new FakerImpl();
        var result = fakerImpl.Create<ClassWithConstructor>();
        result.Should().NotBeNull();
        result.PrivateProperty.Should().NotBe(default);
    }

    [Fact]
    public void ShouldUseDifferentConstructors() {
        var fakerImpl = new FakerImpl();
        var result = fakerImpl.Create<ClassWithBrokenConstructor>();
        result.Should().NotBeNull();
        result.Prop1.Should().NotBe(default);
        result.Prop2.Should().Be(default);
    }


    [Fact]
    public void ShouldFakeUserTypeWithRussianProperty() {
        var fakerImpl = new FakerImpl();
        var result = fakerImpl.Create<ClassWithRussianProperty>();
        result.Should().NotBeNull();
        result.FirstName.Should().NotBeNullOrEmpty();
        result.Age.Should().BeGreaterThan(0);
        result.Children.Should().NotBeNullOrEmpty();
        result.Возраст.Should().BeGreaterThan(0);
    }
}