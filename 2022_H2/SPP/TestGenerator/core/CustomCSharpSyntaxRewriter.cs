using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.CSharp.Syntax;
using Xunit;

namespace core;

class CustomCSharpSyntaxRewriter : CSharpSyntaxRewriter
{
    public List<UsingDirectiveSyntax> usings = new();
    public List<MemberDeclarationSyntax> members = new();

    public override SyntaxNode VisitClassDeclaration(ClassDeclarationSyntax node)
    {
        var cls = (ClassDeclarationSyntax)base.VisitClassDeclaration(node)!;


        List<MemberDeclarationSyntax> methodsDst = new List<MemberDeclarationSyntax>();

        var methodsSrc = cls.Members.OfType<MethodDeclarationSyntax>().ToList();
        foreach (var method in methodsSrc)
        {
            methodsDst.Add(rewriteMethod(method));
        }

        members.Add(GeneratePublicClass(cls.Identifier, methodsDst));

        return cls;
    }

    public override SyntaxNode? VisitUsingDirective(UsingDirectiveSyntax node)
    {
        var usingDirective = (UsingDirectiveSyntax)base.VisitUsingDirective(node)!;
        usings.Add(usingDirective);
        return usingDirective;
    }

    private MethodDeclarationSyntax rewriteMethod(MethodDeclarationSyntax method)
    {
        return SyntaxFactory.MethodDeclaration(
            new SyntaxList<AttributeListSyntax>(),
            method.Modifiers,
            method.ReturnType,
            null,
            method.Identifier,
            null,
            method.ParameterList,
            new SyntaxList<TypeParameterConstraintClauseSyntax>(),
            SyntaxFactory.Block(generateAssertFalseStatement()),
            null);

        var expressions = method.DescendantNodes().OfType<AttributeListSyntax>().ToList();
        var newMethod = method
            .ReplaceNode(
                method.Body!,
                SyntaxFactory.Block(generateAssertFalseStatement())
            )
            .RemoveNodes(expressions, SyntaxRemoveOptions.KeepNoTrivia);
        return newMethod;
    }

    public static ExpressionStatementSyntax generateAssertFalseStatement()
    {
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

    public static ClassDeclarationSyntax GeneratePublicClass(
        SyntaxToken identifier,
        List<MemberDeclarationSyntax> members
    )
    {
        var publicModifier = SyntaxFactory.Token(SyntaxKind.PublicKeyword);
        return SyntaxFactory.ClassDeclaration(
            new SyntaxList<AttributeListSyntax>(), //Attribute list
            new SyntaxTokenList() { publicModifier }, //Modifiers
            identifier, //Identifier
            null, //Type parameter list
            null, //Base list
            new SyntaxList<TypeParameterConstraintClauseSyntax>(), //Constraint clauses list
            SyntaxFactory.List<MemberDeclarationSyntax>(members));
    }
}