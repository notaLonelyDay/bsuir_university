namespace dir_scanner.entity;

public class Directory : StorageItem {
    public Directory(string absolutePath) : base(absolutePath) {
    }

    public List<Directory> subDirs { get; } = new List<Directory>();
    public List<File> files { get; } = new List<File>();
}