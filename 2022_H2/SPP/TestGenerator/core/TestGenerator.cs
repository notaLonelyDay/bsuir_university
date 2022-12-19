using System.Diagnostics.Metrics;
using core.model;
using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.CSharp.Syntax;
using Microsoft.CodeAnalysis.Editing;
using Microsoft.CodeAnalysis.Formatting;

namespace core;

public class TestGenerator {
    public List<GeneratorResult> Generate(string source) {
        SyntaxTree tree = CSharpSyntaxTree.ParseText(source);
        var root = tree.GetCompilationUnitRoot();
        var cSharpSyntaxRewriter = new CustomCSharpSyntaxRewriter();

        root = (CompilationUnitSyntax)cSharpSyntaxRewriter.Visit(root);

        var ans = new List<GeneratorResult>();
        foreach (var clazz in cSharpSyntaxRewriter.classes) {
            ans.Add(new GeneratorResult() {
                content = generateClass(clazz),
                name = clazz.name
            });
        }

        return ans;
    }

    private string generateClass(Clazz clazz) {
        var usingNode = SyntaxFactory.UsingDirective(SyntaxFactory.IdentifierName("Xunit"));

        var clazzNode = SyntaxFactory.ClassDeclaration(
            new SyntaxList<AttributeListSyntax>(),
            new SyntaxTokenList(SyntaxFactory.Token(SyntaxKind.PublicKeyword)),
            SyntaxFactory.Identifier(clazz.name + "Test"),
            null,
            null,
            new SyntaxList<TypeParameterConstraintClauseSyntax>(),
            SyntaxFactory.List(generateMembers(clazz))
        );

        var nameSpaceNode = SyntaxFactory.NamespaceDeclaration(
            SyntaxFactory.ParseName(
                clazz.nameSpace + ".Tests"
            ),
            new SyntaxList<ExternAliasDirectiveSyntax>(),
            new SyntaxList<UsingDirectiveSyntax>(),
            SyntaxFactory.List(new MemberDeclarationSyntax[] { clazzNode })
        );


        var root = SyntaxFactory.CompilationUnit(
            new SyntaxList<ExternAliasDirectiveSyntax>(),
            SyntaxFactory.List(new[] { usingNode }),
            new SyntaxList<AttributeListSyntax>(),
            SyntaxFactory.List(new[] { (MemberDeclarationSyntax)nameSpaceNode }));

        var workspace = new AdhocWorkspace();
        var resultCompilationUnit = (CompilationUnitSyntax)Formatter.Format(root, workspace);
        return resultCompilationUnit.ToString();
    }

    private List<MemberDeclarationSyntax> generateMembers(Clazz clazz) {
        var members = new List<MemberDeclarationSyntax>();

        var membersMap = new Dictionary<String, int>();
        foreach (var method in clazz.methods) {
            var counted = membersMap.GetValueOrDefault(method, 0);
            if (counted == 0)
                membersMap[method] = 1;
            else
                membersMap[method]++;
        }

        var usedCountMap = new Dictionary<String, int>();

        foreach (var methodName in clazz.methods) {
            string newMethodName;
            if (membersMap[methodName] > 1) {
                var usedCount = usedCountMap.GetValueOrDefault(methodName, 0);
                newMethodName = methodName + (usedCount + 1);
                if (usedCount == 0)
                    usedCountMap[methodName] = 1;
                else
                    usedCountMap[methodName]++;
            }
            else {
                newMethodName = methodName;
            }

            var newIdentifier = SyntaxFactory.Identifier(newMethodName + "Test");
            var member = SyntaxFactory.MethodDeclaration(
                new SyntaxList<AttributeListSyntax>(
                    SyntaxFactory.AttributeList(
                        new SeparatedSyntaxList<AttributeSyntax>().Add(
                            SyntaxFactory.Attribute(SyntaxFactory.ParseName("Fact"))
                        )
                    )
                ),
                new SyntaxTokenList(SyntaxFactory.Token(SyntaxKind.PublicKeyword)),
                SyntaxFactory.PredefinedType(SyntaxFactory.Token(SyntaxKind.VoidKeyword)),
                null,
                newIdentifier,
                null,
                SyntaxFactory.ParameterList(),
                new SyntaxList<TypeParameterConstraintClauseSyntax>(),
                SyntaxFactory.Block(generateAssertFalseStatement()),
                null);
            members.Add(member);
        }

        return members;
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

    public static TestGenerator shared = new TestGenerator();

    public record GeneratorResult {
        public string name;
        public string content;
    }
}