﻿using System.Text;
using Tracer.Core;
using Tracer.Serialization.Abstractions;

namespace Tracer.Serialization.Json;

public class JsonSerializer : ITraceResultSerializer
{
    public void Serialize(Core.TraceResult traceResult, Stream to)
    {
        var res = System.Text.Json.JsonSerializer.Serialize(new TraceResult(traceResult));
        to.Write(Encoding.Default.GetBytes(res));
    }

    public string Format => "Json";
}