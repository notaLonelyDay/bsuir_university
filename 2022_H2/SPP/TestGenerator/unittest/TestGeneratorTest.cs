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
    public void ShouldGenerateCorrectTests() {
        var srcDir = @"C:\Users\user\Desktop\uni\bsuir_university\2022_H2\SPP\TestGenerator\unittest\input";
        var correctDir = @"C:\Users\user\Desktop\uni\bsuir_university\2022_H2\SPP\TestGenerator\unittest\output\";

        var srcFiles = Directory.GetFiles(srcDir);
        foreach (var srcFile in srcFiles) {
            var fileName = Path.GetFileName(srcFile);
            var correctFile = correctDir + fileName;

            var src = File.ReadAllText(srcFile).Trim();
            var correct = File.ReadAllText(correctFile).Trim();

            var generated = TestGenerator.shared.Generate(src).Trim();
            Assert.Equal(correct, generated);
        }
    }
}