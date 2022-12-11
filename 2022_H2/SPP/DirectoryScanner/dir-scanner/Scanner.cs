using Directory = dir_scanner.entity.Directory;

namespace dir_scanner;

public interface DirScanner {
    Directory startScan(string path, int threadCount);
    void cancel();
}