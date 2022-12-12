using System.Collections.Concurrent;
using dir_scanner.util;
using Directory = dir_scanner.entity.Directory;
using File = dir_scanner.entity.File;
using Xunit;

namespace dir_scannerTest;

public class DirScannerImplTest
{
    [Fact]
    public Directory startScanTest(string path, int threadCount = 8)
    {
        Assert.True(false);
    }

    [Fact]
    public void cancelTest()
    {
        Assert.True(false);
    }

    [Fact]
    public void throwIfInvalidParamsTest(string path, int threadCount)
    {
        Assert.True(false);
    }

    [Fact]
    public void processDirTest(ref Directory dir, CancellationToken cancellationToken)
    {
        Assert.True(false);
    }

    [Fact]
    public void calcSizesTest(Directory dir)
    {
        Assert.True(false);
    }
}