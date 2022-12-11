using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.CSharp.Syntax;
using Xunit;

namespace core;

class CustomCSharpSyntaxRewriter : CSharpSyntaxRewriter {
    public override SyntaxNode VisitClassDeclaration(ClassDeclarationSyntax node) {
        var cls = (ClassDeclarationSyntax)base.VisitClassDeclaration(node)!;

        var methods = cls.Members.OfType<MethodDeclarationSyntax>().ToList();
        foreach (var method in methods) {
            var newMethod = rewriteMethod(method);
            cls = cls.ReplaceNode(method, newMethod);
        }

        return cls;
    }

    private MethodDeclarationSyntax rewriteMethod(MethodDeclarationSyntax method) {
        var expressions = method.DescendantNodes().OfType<AttributeListSyntax>().ToList();
        var newMethod = method
            .ReplaceNode(
                method.Body!,
                SyntaxFactory.Block(generateAssertFalseStatement())
                )
            .RemoveNodes(expressions,SyntaxRemoveOptions.KeepNoTrivia)
            .InsertNodesBefore(method.Body);
        return newMethod;
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