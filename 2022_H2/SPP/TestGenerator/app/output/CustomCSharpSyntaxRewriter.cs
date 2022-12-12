using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.CSharp.Syntax;
using Xunit;

namespace coreTest;

public class CustomCSharpSyntaxRewriterTest
{
    [Fact]
    public SyntaxNode? VisitNamespaceDeclarationTest(NamespaceDeclarationSyntax node)
    {
        Assert.True(false);
    }

    [Fact]
    public SyntaxNode? VisitFileScopedNamespaceDeclarationTest(FileScopedNamespaceDeclarationSyntax node)
    {
        Assert.True(false);
    }

    [Fact]
    public SyntaxNode VisitClassDeclarationTest(ClassDeclarationSyntax node)
    {
        Assert.True(false);
    }

    [Fact]
    public SyntaxNode? VisitMethodDeclarationTest(MethodDeclarationSyntax node)
    {
        Assert.True(false);
    }

    [Fact]
    public SyntaxNode? VisitUsingDirectiveTest(UsingDirectiveSyntax node)
    {
        Assert.True(false);
    }

    [Fact]
    public ExpressionStatementSyntax generateAssertFalseStatementTest()
    {
        Assert.True(false);
    }
}