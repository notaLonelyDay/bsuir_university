using System;
using System.Threading.Tasks;
using System.Windows.Input;
using dir_scanner;
using DirectoryScanner.util;
using DirectoryScanner.vo;
using Ookii.Dialogs.Wpf;

namespace DirectoryScanner.command;

public class StartScannerCommand : ICommand {
    private readonly Action<FileSystemVO> _action;
    private readonly DirScanner _scanner;

    public StartScannerCommand(DirScanner scanner, Action<FileSystemVO> action) {
        _scanner = scanner;
        _action = action;
    }

    public event EventHandler CanExecuteChanged {
        add => CommandManager.RequerySuggested += value;
        remove => CommandManager.RequerySuggested -= value;
    }

    public bool CanExecute(object? parameter) {
        return true;
    }

    public void Execute(object? parameter) {
        var dialog = new VistaFolderBrowserDialog();
        if (dialog.ShowDialog().GetValueOrDefault())
            Task.Run(() => {
                var result = _scanner.startScan(dialog.SelectedPath, 8);
                var resultVO = ScannerUtil.ResultToVO(result);
                _action.Invoke(resultVO);
            });
    }
}