using System;
using System.Windows.Input;
using dir_scanner;

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