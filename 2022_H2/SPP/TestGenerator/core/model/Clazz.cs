namespace core.model;

record Clazz {
    public string name;
    public string? nameSpace;
    public List<string> methods = new();
}