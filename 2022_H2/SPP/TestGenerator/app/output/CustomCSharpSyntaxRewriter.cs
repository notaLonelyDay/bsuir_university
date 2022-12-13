using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.CSharp.Syntax;
using Xunit;

namespace coreTest;

public class CustomCSharpSyntaxRewriterTest
{
    [Fact]
    public SyntaxNode? VisitNamespaceDeclarationTest()
    {
        Assert.True(false);
    }

    [Fact]
    public SyntaxNode? VisitFileScopedNamespaceDeclarationTest()
    {
        Assert.True(false);
    }

    [Fact]
    public SyntaxNode VisitClassDeclarationTest()
    {
        Assert.True(false);
    }

    [Fact]
    public SyntaxNode? VisitMethodDeclarationTest()
    {
        Assert.True(false);
    }

    [Fact]
    public SyntaxNode? VisitUsingDirectiveTest()
    {
        Assert.True(false);
    }

    [Fact]
    public ExpressionStatementSyntax generateAssertFalseStatementTest()
    {
        Assert.True(false);
    }
}