using System.Reflection;
using Tracer.Serialization.Abstractions;

namespace Tracer.Serialization;

public static class TraceResultSerializerLoader
{
    public static IEnumerable<ITraceResultSerializer> Load(IEnumerable<string> files)
    {
        var serializers = new List<ITraceResultSerializer>();
        foreach (var file in files)
        {
            var serializerAssembly = Assembly.LoadFrom(file);
            var types = serializerAssembly.GetTypes();
            foreach (var type in types)
            {
                if (!typeof(ITraceResultSerializer).IsAssignableFrom(type))
                    continue;
                var serializer = (ITraceResultSerializer?)Activator.CreateInstance(type);
                if (serializer == null) throw new Exception($"Serializer {type} not created");

                serializers.Add(serializer);
            }
        }

        return serializers;
    }
}