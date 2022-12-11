using System.Windows;
using DirectoryScanner.vm;

namespace DirectoryScanner;

/// <summary>
///     Interaction logic for MainWindow.xaml
/// </summary>
public partial class MainWindow : Window {
    public MainWindow() {
        InitializeComponent();
        DataContext = new MainViewModel();
    }
}