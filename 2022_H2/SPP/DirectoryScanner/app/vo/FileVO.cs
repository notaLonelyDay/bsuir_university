namespace DirectoryScanner.vo;

public class FileVO : FileSystemVO {
    public FileVO(string name, string size, float sizeInPercents) {
        Name = name;
        Size = size;
        SizeInPercents = sizeInPercents;
    }

    public string Name { get; }
    public string Size { get; }
    public float SizeInPercents { get; }
}