using FluentAssertions;
using Xunit;
using StringFormatter = core.StringFormatter;

namespace tests;

public class StringFormatterTests
{

    [Theory]
    [InlineData("{")]
    [InlineData("}")]
    [InlineData("{{}")]
    [InlineData("{}}")]
    [InlineData("{[}")]
    [InlineData("{]}")]
    public void Format_ShouldThrowWrongStringException(string input)
    {
        Assert.Throws<FormatException>(() => StringFormatter.shared.Format(input, new object()));
    }

    [Fact]
    public void Format_ShouldSucceed()
    {
        Account fake = new() { money = 10 };
        var input = "{{money}} is {money}";
        var expected = "{money} is 10";

        var result = StringFormatter.shared.Format(input, fake);

        result.Should().NotBeNullOrEmpty();
        result.Should().BeEquivalentTo(expected);
    }

    [Fact]
    public void Format_ShouldSucceedWithMultipleInterpolationUnits()
    {
        Account fake = new()
        {
            username = "user1",
            money = 20_000
        };

        var input = "{{money}} is {money}, {{username}} is {username}";
        var expected = "{money} is 20000, {username} is user1";

        var result = StringFormatter.shared.Format(input, fake);

        result.Should().NotBeNullOrEmpty();
        result.Should().BeEquivalentTo(expected);
    }
}