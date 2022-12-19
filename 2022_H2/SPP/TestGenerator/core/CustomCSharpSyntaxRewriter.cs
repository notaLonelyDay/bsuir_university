using core.model;
using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.CSharp.Syntax;

namespace core;

class CustomCSharpSyntaxRewriter : CSharpSyntaxRewriter {
    public List<Clazz> classes = new List<Clazz>();

    public string? globalNamespace = null;

    // public override SyntaxNode? VisitNamespaceDeclaration(NamespaceDeclarationSyntax node) {
    //     var namespaceNode = (NamespaceDeclarationSyntax)base.VisitNamespaceDeclaration(node)!;
    //     var uselessNodes =
    //         namespaceNode
    //             .ChildNodes()
    //             .Where(
    //                 n => n
    //                          is not ClassDeclarationSyntax &&
    //                      n is not NamespaceDeclarationSyntax &&
    //                      n is not QualifiedNameSyntax
    //             )!;
    //     namespaceNode = namespaceNode.RemoveNodes(uselessNodes, SyntaxRemoveOptions.KeepNoTrivia)!;
    //
    //     var newName = SyntaxFactory.ParseName(
    //         namespaceNode.Name + ".Tests"
    //     );
    //     return SyntaxFactory.NamespaceDeclaration(
    //         newName,
    //         new SyntaxList<ExternAliasDirectiveSyntax>(),
    //         new SyntaxList<UsingDirectiveSyntax>(),
    //         namespaceNode.Members
    //     );
    // }

    public override SyntaxNode? VisitFileScopedNamespaceDeclaration(FileScopedNamespaceDeclarationSyntax node) {
        var namespaceNode = (FileScopedNamespaceDeclarationSyntax)base.VisitFileScopedNamespaceDeclaration(node)!;
        globalNamespace = namespaceNode.Name.ToString();
        return namespaceNode;
        var fileScopedNamespaceDeclarationSyntax = SyntaxFactory.FileScopedNamespaceDeclaration(
            new SyntaxList<AttributeListSyntax>(),
            new SyntaxTokenList(),
            namespaceNode.NamespaceKeyword,
            SyntaxFactory.ParseName(
                namespaceNode.Name + ".Tests"
            ),
            namespaceNode.SemicolonToken,
            new SyntaxList<ExternAliasDirectiveSyntax>(),
            namespaceNode.Usings,
            namespaceNode.Members
        );
        return fileScopedNamespaceDeclarationSyntax;
    }

    private string? getClassNameSpace(ClassDeclarationSyntax node) {
        var parent = node.Parent;
        while (parent != null && parent.GetType() != typeof(NamespaceDeclarationSyntax)
                              && parent.GetType() != typeof(FileScopedNamespaceDeclarationSyntax)) {
            parent = parent.Parent;
        }

        if (parent != null && parent is NamespaceDeclarationSyntax syntax)
            return syntax.Name.ToString();
        else if (parent != null && parent is FileScopedNamespaceDeclarationSyntax declarationSyntax)
            return declarationSyntax.Name.ToString();
        else
            return globalNamespace ?? null;
    }

    public override SyntaxNode VisitClassDeclaration(ClassDeclarationSyntax node) {
        var clsNode = (ClassDeclarationSyntax)base.VisitClassDeclaration(node)!;

        var clazz = new Clazz() {
            name = clsNode.Identifier.ValueText,
            nameSpace = getClassNameSpace(clsNode)
        };


        var publicMethods = clsNode.ChildNodes().OfType<MethodDeclarationSyntax>().Where(
            m => m.Modifiers.Any(SyntaxKind.PublicKeyword)
        );
        foreach (var method in publicMethods) {
            clazz.methods.Add(method.Identifier.ValueText);
        }

        classes.Add(clazz);

        return clsNode;
    }

    // public override SyntaxNode? VisitUsingDirective(UsingDirectiveSyntax node) {
    //     var usingDirective = (UsingDirectiveSyntax)base.VisitUsingDirective(node)!;
    //     return usingDirective;
    // }
}