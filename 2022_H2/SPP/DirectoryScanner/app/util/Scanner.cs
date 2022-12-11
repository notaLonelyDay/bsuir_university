using dir_scanner.entity;
using DirectoryScanner.vo;

namespace DirectoryScanner.util;

public static class ScannerUtil {
    public static FileSystemVO ResultToVO(Directory node, Directory? parent = null) {
        var item = new DirectoryVO(
            node.absolutePath,
            formatSize(node),
            ((float)node.size / parent?.size ?? 1) * 100
        );

        foreach (var child in node.subDirs) {
            var childResult = ResultToVO(child, node);
            item.Directories.Add(childResult);
        }

        foreach (var file in node.files) {
            var childItem = new FileVO(
                file.absolutePath,
                formatSize(file),
                (float)file.size * 100 / node.size
            );
            item.Directories.Add(childItem);
        }

        return item;
    }

    private static string formatSize(StorageItem item) {
        var sizeStr = toHumanSize(item.size);
        if (!item.isSizeFinal) sizeStr += "...";

        return sizeStr;
    }

    private static string toHumanSize(long size) {
        string[] sizes = { "B", "KB", "MB", "GB", "TB" };
        var order = 0;
        while (size >= 1024 && order < sizes.Length - 1) {
            order++;
            size = size / 1024;
        }

        return $"{size:0.##} {sizes[order]}";
    }
}