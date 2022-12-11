using core;
using Xunit;
using Xunit.Abstractions;

namespace unittest;

public class TestGeneratorTest {
    private readonly ITestOutputHelper testOutputHelper;
    public TestGeneratorTest(ITestOutputHelper testOutputHelper) {
        this.testOutputHelper = testOutputHelper;
    }

    public const string DefaultFile = @"namespace Lepesh.Lepesh
        {
            class LepeshClass
            {
                [Fact]
                public void LepeshMethod()
                {
                    int a = 5;
                }
            }
        }";

    [Fact]
    public void ShouldGenerateTestFile() {
        testOutputHelper.WriteLine(TestGenerator.shared.Generate(DefaultFile));
        Assert.True(false);
    }
}