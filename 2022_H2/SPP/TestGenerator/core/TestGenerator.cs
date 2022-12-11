using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.CSharp.Syntax;
using Microsoft.CodeAnalysis.Editing;
using Microsoft.CodeAnalysis.Formatting;

namespace core;

public class TestGenerator {
    public string Generate(string source) {
        SyntaxTree tree = CSharpSyntaxTree.ParseText(source);
        var root = tree.GetCompilationUnitRoot();
        var cSharpSyntaxRewriter = new CustomCSharpSyntaxRewriter();
        root = (CompilationUnitSyntax)cSharpSyntaxRewriter.Visit(root);

        var workspace = new AdhocWorkspace();
        var resultCompilationUnit = (CompilationUnitSyntax)Formatter.Format(root, workspace);
        return resultCompilationUnit.ToString();
    }


    public static TestGenerator shared = new TestGenerator();
}