using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Windows.Input;
using dir_scanner;
using DirectoryScanner.command;
using DirectoryScanner.vo;

namespace DirectoryScanner.vm;

public class MainViewModel : INotifyPropertyChanged {
    private ICommand? _cancelScanCommand;
    private readonly DirScanner _scanner;
    private ObservableCollection<FileSystemVO> _scanResult = new();
    private ICommand? _startScanCommand;

    public MainViewModel() {
        _scanner = new DirScannerImpl();
    }

    public ObservableCollection<FileSystemVO> scanResult {
        get => _scanResult;
        set {
            _scanResult = value;
            notify(nameof(scanResult));
        }
    }

    public ICommand startScanCommand {
        get {
            return _startScanCommand ??= new StartScannerCommand(_scanner,
                delegate(FileSystemVO i) { scanResult = new ObservableCollection<FileSystemVO> { i }; });
        }
    }

    public ICommand cancelScanCommand {
        get { return _cancelScanCommand ??= new CancelScannerCommand(_scanner); }
    }

    public event PropertyChangedEventHandler? PropertyChanged;

    protected void notify(string propName) {
        PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propName));
    }
}