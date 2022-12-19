using core;
using Xunit;
using Xunit.Abstractions;

namespace unittest;

public class TestGeneratorTest {
    private readonly ITestOutputHelper testOutputHelper;

    public TestGeneratorTest(ITestOutputHelper testOutputHelper) {
        this.testOutputHelper = testOutputHelper;
    }

    [Fact]
    public void ShouldGenerateCorrectSingleTests() {
        var srcDir = @"C:\Users\user\Desktop\uni\bsuir_university\2022_H2\SPP\TestGenerator\unittest\input\";
        var correctDir = @"C:\Users\user\Desktop\uni\bsuir_university\2022_H2\SPP\TestGenerator\unittest\output\";

        var srcFileNames = new string[] {
            "CustomCSharpSyntaxRewriter.cs",
            "DirScannerImpl.cs",
            "MainViewModel.cs",
            "ScannerTests.cs",
            "TestGenerator.cs"
        };
        foreach (var srcFileName in srcFileNames) {
            var srcFile = srcDir + srcFileName;
            var fileName = Path.GetFileName(srcFile);
            var correctFile = correctDir + fileName;

            var src = File.ReadAllText(srcFile).Trim();
            var correct = File.ReadAllText(correctFile).Trim();

            var generated = TestGenerator.shared.Generate(src).First().content.Trim();
            Assert.Equal(correct, generated);
        }
    }

    [Fact]
    public void ShouldGenerateCorrectMultiTest() {
        var srcDir = @"C:\Users\user\Desktop\uni\bsuir_university\2022_H2\SPP\TestGenerator\unittest\input\";
        var correctDir = @"C:\Users\user\Desktop\uni\bsuir_university\2022_H2\SPP\TestGenerator\unittest\output\";

        string srcFileContent = File.ReadAllText(srcDir + "MyCode.cs");

        var dstContent = new string[] {
            File.ReadAllText(correctDir + "MyClass.cs"),
            File.ReadAllText(correctDir + "MyClass2.cs")
        };

        var generated = TestGenerator.shared.Generate(srcFileContent);
        Assert.Equal(generated.Where(i => i.name == "MyClass").First().content, dstContent[0]);
        Assert.Equal(generated.Where(i => i.name == "MyClass2").First().content, dstContent[1]);
    }
}