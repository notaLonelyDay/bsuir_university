// See https://aka.ms/new-console-template for more information

using dir_scanner;
using dir_scanner.entity;
using dir_scanner.util;
using FluentAssertions;
using Xunit;
using Xunit.Abstractions;

public class ScannerTests {
    private readonly ITestOutputHelper testOutputHelper;

    public ScannerTests(ITestOutputHelper testOutputHelper) {
        this.testOutputHelper = testOutputHelper;
    }

    [Fact]
    public void ShouldWorkIfValid() {
        var curDir = AppDomain.CurrentDomain.BaseDirectory;
        var scanner = new DirScannerImpl();
        var result = scanner.startScan(curDir);

        result.Should().NotBeNull();
        testOutputHelper.WriteLine(result.files.ToString());
        result.absolutePath.Should().BeEquivalentTo(curDir);
    }

    [Fact]
    public void ShouldThrowIfPathNotValid() {
        var path = "never gonna give you up";
        var scanner = new DirScannerImpl();

        Action result = () => scanner.startScan(path);
        result.Should().Throw<PathNotExistsException>();
    }

    [Fact]
    public void ShouldThrowIfThreadsNotValid() {
        var curDir = AppDomain.CurrentDomain.BaseDirectory;
        var scanner = new DirScannerImpl();

        Action result = () => scanner.startScan(curDir, -3);
        result.Should().Throw<InvalidThreadCountException>();
    }
}