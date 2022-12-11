namespace DirectoryScanner.vo;

public interface FileSystemVO {
    string Name { get; }
    string Size { get; }
    float SizeInPercents { get; }
}