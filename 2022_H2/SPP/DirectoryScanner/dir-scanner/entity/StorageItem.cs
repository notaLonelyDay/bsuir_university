namespace dir_scanner.entity;

public class StorageItem {
    public StorageItem(string absolutePath) {
        this.absolutePath = absolutePath;
        isSizeFinal = false;
    }

    public string absolutePath { get; set; }
    public long size { get; set; } = 0;
    public bool isSizeFinal { get; set; } = false;
    public bool isSymlink { get; set; } = false;

}