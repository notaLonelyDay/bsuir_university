using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.CSharp.Syntax;

namespace core;

class CustomCSharpSyntaxRewriter : CSharpSyntaxRewriter {
    public override SyntaxNode? VisitNamespaceDeclaration(NamespaceDeclarationSyntax node) {
        var namespaceNode = (NamespaceDeclarationSyntax)base.VisitNamespaceDeclaration(node)!;
        var uselessNodes =
            namespaceNode
                .ChildNodes()
                .Where(
                    n => n
                             is not ClassDeclarationSyntax &&
                         n is not NamespaceDeclarationSyntax &&
                         n is not QualifiedNameSyntax
                )!;
        namespaceNode = namespaceNode.RemoveNodes(uselessNodes, SyntaxRemoveOptions.KeepNoTrivia)!;

        var newName = SyntaxFactory.ParseName(
            namespaceNode.Name + "Test"
        );
        return SyntaxFactory.NamespaceDeclaration(
            newName,
            new SyntaxList<ExternAliasDirectiveSyntax>(),
            new SyntaxList<UsingDirectiveSyntax>(),
            namespaceNode.Members
        );
    }

    public override SyntaxNode? VisitFileScopedNamespaceDeclaration(FileScopedNamespaceDeclarationSyntax node) {
        var namespaceNode = (FileScopedNamespaceDeclarationSyntax) base.VisitFileScopedNamespaceDeclaration(node)!;
        return SyntaxFactory.FileScopedNamespaceDeclaration(
                new SyntaxList<AttributeListSyntax>(),
                new SyntaxTokenList(),
                namespaceNode.NamespaceKeyword,
                SyntaxFactory.ParseName(
                    namespaceNode.Name + "Test"
                ),
                namespaceNode.SemicolonToken,
                new SyntaxList<ExternAliasDirectiveSyntax>(),
                namespaceNode.Usings,
                namespaceNode.Members
            );
    }

    public override SyntaxNode VisitClassDeclaration(ClassDeclarationSyntax node) {
        var clsNode = (ClassDeclarationSyntax)base.VisitClassDeclaration(node)!;
        var uselessNodes =
            clsNode
                .ChildNodes()
                .Where(
                    n => n
                        is not MethodDeclarationSyntax
                );
        clsNode = clsNode.RemoveNodes(uselessNodes, SyntaxRemoveOptions.KeepNoTrivia);


        var nonPublicMethods = clsNode.ChildNodes().OfType<MethodDeclarationSyntax>().Where(
            m => !m.Modifiers.Any(SyntaxKind.PublicKeyword)
        ).ToList();
        clsNode = clsNode.RemoveNodes(nonPublicMethods, SyntaxRemoveOptions.KeepNoTrivia);

        var newIdentifier = SyntaxFactory.Identifier(clsNode.Identifier.ValueText + "Test");
        return SyntaxFactory.ClassDeclaration(
                new SyntaxList<AttributeListSyntax>(),
                new SyntaxTokenList(SyntaxFactory.Token(SyntaxKind.PublicKeyword)),
                newIdentifier,
                null,
                null,
                new SyntaxList<TypeParameterConstraintClauseSyntax>(),
                clsNode.Members)
            ;
    }

    public override SyntaxNode? VisitMethodDeclaration(MethodDeclarationSyntax node) {
        var method = (MethodDeclarationSyntax) base.VisitMethodDeclaration(node)!;
        var newIdentifier = SyntaxFactory.Identifier(method.Identifier.ValueText + "Test");
        return SyntaxFactory.MethodDeclaration(
            new SyntaxList<AttributeListSyntax>(
                SyntaxFactory.AttributeList(
                    new SeparatedSyntaxList<AttributeSyntax>().Add(
                        SyntaxFactory.Attribute(SyntaxFactory.ParseName("Fact"))
                    )
                )
            ),
            new SyntaxTokenList(SyntaxFactory.Token(SyntaxKind.PublicKeyword)),
            method.ReturnType,
            null,
            newIdentifier,
            null,
            SyntaxFactory.ParameterList(),
            new SyntaxList<TypeParameterConstraintClauseSyntax>(),
            SyntaxFactory.Block(generateAssertFalseStatement()),
            null);
    }

    public override SyntaxNode? VisitUsingDirective(UsingDirectiveSyntax node) {
        var usingDirective = (UsingDirectiveSyntax)base.VisitUsingDirective(node)!;
        return usingDirective;
    }

    public static ExpressionStatementSyntax generateAssertFalseStatement() {
        return SyntaxFactory.ExpressionStatement(
            SyntaxFactory.InvocationExpression(
                    SyntaxFactory.MemberAccessExpression(
                        SyntaxKind.SimpleMemberAccessExpression,
                        SyntaxFactory.IdentifierName("Assert"),
                        SyntaxFactory.IdentifierName("True")))
                .WithArgumentList(
                    SyntaxFactory.ArgumentList(
                        SyntaxFactory.SingletonSeparatedList<ArgumentSyntax>(
                            SyntaxFactory.Argument(
                                SyntaxFactory.LiteralExpression(
                                    SyntaxKind.StringLiteralExpression,
                                    SyntaxFactory.Token(SyntaxKind.FalseKeyword)))))));
    }
}