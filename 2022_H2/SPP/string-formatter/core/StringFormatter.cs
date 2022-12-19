using System.Collections.Concurrent;
using System.Linq.Expressions;
using System.Text;
using core.entity;

namespace core;

public interface IStringFormatter {
    // ReSharper disable once InconsistentNaming
    string Format(string template, object target);
}

public class StringFormatter : IStringFormatter {
    private const char OPEN = '{';
    private const char CLOSE = '}';

    public static StringFormatter shared = new StringFormatter();

    private ConcurrentDictionary<FormatterKey, Delegate?> cache = new();

    public string Format(string template, object target) {
        return processString(template, target);
    }

    private string processString(string template, object target) {
        var ans = new StringBuilder();

        var opened = 0;

        bool inBraces = false;
        var expression = new StringBuilder();

        for (int i = 0; i < template.Length; i++) {
            var ch = template[i];
            if (ch == OPEN) {
                if (inBraces) {
                    // if in braces and previous char is OPEN should escape
                    if (template[i - 1] == OPEN) {
                        opened--;
                        ans.Append(OPEN);
                        inBraces = false;
                    }
                    // previous is not OPEN
                    else {
                        throw new FormatException($"Parse error at position={i}." +
                                                  $" Unpaired open bracket.");
                    }
                }
                else {
                    inBraces = true;
                    opened++;
                }
            }
            else if (ch == CLOSE) {
                if (inBraces) {
                    inBraces = false;
                    opened--;
                    ans.Append(getMember(expression.ToString(), target));
                    expression.Clear();
                }
                else {
                    // escaping closing if not in braces
                    if (i + 1 < template.Length && template[i + 1] == CLOSE) {
                        ans.Append(CLOSE);
                        i++;
                    }
                    else {
                        throw new FormatException($"Parse error at position={i}." +
                                                  $" Unpaired close bracket.");
                    }
                }
            }
            else {
                if (inBraces) {
                    expression.Append(ch);
                }
                else {
                    ans.Append(ch);
                }
            }
        }

        if (opened != 0) {
            throw new FormatException("Unmatched open bracket.");
        }

        return ans.ToString();
    }

    // with caching
    private string getMember(string memberName, object target) {
        var type = target.GetType();
        var formatterKey = new FormatterKey() { type = type, member = memberName };

        // return if cached
        if (cache.TryGetValue(
                formatterKey,
                out var deleg
            )
           ) {
            return (string)deleg!.DynamicInvoke(target)!;
        }

        // save if not cached
        var del = generateDelegate(type, memberName);
        cache.TryAdd(formatterKey, del);

        return (string)del!.DynamicInvoke(target)!;
    }

    private Delegate generateDelegate(Type targetType, string memberName) {
        var targetTypeParameter = Expression.Parameter(targetType);
        var memberInfo = targetType.GetMember(memberName).FirstOrDefault()
                         ?? throw new FormatException($"Target {targetType.Name} dos not have member {memberName}");
        var memberAccess = Expression.MakeMemberAccess(targetTypeParameter, memberInfo);
        var methodCall = Expression.Call(memberAccess, memberInfo.DeclaringType!.GetMethod("ToString")!);
        var delegateType = Expression.GetDelegateType(targetType, typeof(string));
        return Expression.Lambda(delegateType, methodCall, targetTypeParameter).Compile();
    }

    public void clearCache() {
        cache.Clear();
    }
}