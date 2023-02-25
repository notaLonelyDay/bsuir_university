using System.Text;
using Tracer.Core;
using Tracer.Serialization.Abstractions;
using YamlDotNet.Serialization;

namespace Tracer.Serialization.Yaml;

public class YamlSerializer : ITraceResultSerializer
{
    public void Serialize(Core.TraceResult traceResult, Stream to)
    {
        var serializer = new SerializerBuilder().DisableAliases().Build();
        var result = serializer.Serialize(new TraceResult(traceResult));
        to.Write(Encoding.UTF8.GetBytes(result));
    }

    public string Format { get; } = "Yaml";
}