using System.Collections.Concurrent;
using dir_scanner.util;
using Directory = dir_scanner.entity.Directory;
using File = dir_scanner.entity.File;

namespace dir_scanner;

public class DirScannerImpl : DirScanner
{
    private CancellationTokenSource? cancellationTokenSource;
    private readonly ConcurrentQueue<Directory> queue = new();


    public Directory startScan(string path, int threadCount = 8)
    {
        throwIfInvalidParams(path, threadCount);
        var semaphore = new SemaphoreSlim(threadCount, threadCount);
        cancellationTokenSource = new CancellationTokenSource();
        var root = new Directory(path);
        queue.Enqueue(root);
        while ((!queue.IsEmpty || semaphore.CurrentCount != threadCount) &&
               !cancellationTokenSource.IsCancellationRequested)
        {
            Directory? item;
            queue.TryDequeue(out item);
            if (item != null)
            {
                semaphore.Wait();
                var tt = Task.Run(() =>
                {
                    processDir(ref item, cancellationTokenSource.Token);
                    semaphore.Release();
                });
            }
        }

        while (semaphore.CurrentCount != threadCount)
        {
            Thread.Sleep(200);
        }

        calcSizes(root);
        return root;
    }

    public void cancel()
    {
        cancellationTokenSource?.Cancel();
        queue.Clear();
    }

    private void throwIfInvalidParams(string path, int threadCount)
    {
        if (System.IO.Directory.Exists(path) == false)
            throw new PathNotExistsException();
        if (threadCount <= 0)
            throw new InvalidThreadCountException();
    }

    private void processDir(ref Directory dir, CancellationToken cancellationToken)
    {
        try
        {
            cancellationToken.ThrowIfCancellationRequested();
            var dirInfo = new DirectoryInfo(dir.absolutePath);

            // process symlink
            if (dirInfo.LinkTarget != null)
            {
                dir.isSymlink = true;
                dir.size = 0;
                dir.isSizeFinal = true;
                return;
            }

            // add dirs to queue
            foreach (var subDirInfo in dirInfo.EnumerateDirectories())
            {
                var subDir = new Directory(subDirInfo.FullName);
                dir.subDirs.Add(subDir);
                queue.Enqueue(subDir);
                Console.WriteLine("Queue added");
            }

            // add files to this dir
            foreach (var fileInfo in dirInfo.EnumerateFiles())
            {
                var file = new File(fileInfo.FullName);
                dir.files.Add(file);
                if (fileInfo.LinkTarget == null)
                {
                    file.size = fileInfo.Length;
                    file.isSizeFinal = true;
                    file.isSymlink = false;
                }
                else
                {
                    file.size = 0;
                    file.isSizeFinal = true;
                    file.isSymlink = true;
                }
            }

            dir.isSizeFinal = true;
        }
        catch (Exception e)
        {
            Console.WriteLine("Cancelled");
        }
    }

    private void calcSizes(Directory dir)
    {
        var isSizeFinal = true;

        // update from sub dirs
        foreach (var subDir in dir.subDirs)
        {
            calcSizes(subDir);
            isSizeFinal &= subDir.isSizeFinal;
            dir.size += subDir.size;
        }

        // update from files
        foreach (var file in dir.files)
        {
            isSizeFinal &= file.isSizeFinal;
            dir.size += file.size;
        }

        dir.isSizeFinal = isSizeFinal;
    }
}