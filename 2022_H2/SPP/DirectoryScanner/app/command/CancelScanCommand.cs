using System;
using System.Collections.ObjectModel;
using System.IO;
using System.Threading.Tasks;
using System.Windows.Input;
using dir_scanner;
using DirectoryScanner.util;
using DirectoryScanner.vo;
using Directory = dir_scanner.entity.Directory;

namespace DirectoryScanner.command;

public class CancelScannerCommand : ICommand {
    private readonly DirScanner _scanner;

    public CancelScannerCommand(DirScanner scanner) {
        _scanner = scanner;
    }

    public event EventHandler CanExecuteChanged {
        add => CommandManager.RequerySuggested += value;
        remove => CommandManager.RequerySuggested -= value;
    }

    public bool CanExecute(object? parameter) {
        return true;
    }

    public void Execute(object? parameter) {
        _scanner.cancel();
    }
}