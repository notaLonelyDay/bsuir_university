using core;
using Xunit;
using Xunit.Abstractions;

namespace unittest;
public class TestGeneratorTest { private readonly ITestOutputHelper testOutputHelper;
    public TestGeneratorTest(ITestOutputHelper testOutputHelper) {
        this.testOutputHelper = testOutputHelper;
    }

    public const string DefaultFile = @"

        using core;
using Xunit;
using Xunit.Abstractions;    

namespace Lepesh.Lepesh
        {
            class LepeshClass
            {
                public void LepeshMethod()
                {
                    int a = 5;
                }

private void LepeshMethodPriv()
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