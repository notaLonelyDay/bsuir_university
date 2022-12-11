using System.Collections.ObjectModel;

namespace DirectoryScanner.vo;

public class DirectoryVO : FileSystemVO {
    public DirectoryVO(string name, string size, float sizeInPercents) {
        Directories = new ObservableCollection<FileSystemVO>();
        Name = name;
        Size = size;
        SizeInPercents = sizeInPercents;
    }

    public string Name { get; }
    public string Size { get; }
    public float SizeInPercents { get; }
    public ObservableCollection<FileSystemVO> Directories { get; }
}