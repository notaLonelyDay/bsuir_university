using System.Text;
using System.Threading.Tasks.Dataflow;
using core;
using TestsGenerator.Example.Models;

public class App {
    public static async Task Main(string[] args) {
        int readFromFileRestriction = 10;
        int generateTestFileRestriction = 10;
        int writeToFileRestriction = 10;

        var input = Directory.GetFiles(@"C:\Users\user\Desktop\uni\bsuir_university\2022_H2\SPP\TestGenerator\app\input");
        var generator = TestGenerator.shared;
        var output = @"C:\Users\user\Desktop\uni\bsuir_university\2022_H2\SPP\TestGenerator\app\output";

        var readFromFileBlockOptions = new ExecutionDataflowBlockOptions()
            { MaxDegreeOfParallelism = readFromFileRestriction };
        var generateTestFileOptions = new ExecutionDataflowBlockOptions()
            { MaxDegreeOfParallelism = generateTestFileRestriction };
        var writeToFileBlockOptions = new ExecutionDataflowBlockOptions()
            { MaxDegreeOfParallelism = writeToFileRestriction };

        var readFromFileBlock = new TransformBlock<string, ReadFromFileOutput>(async path => {
                Console.WriteLine($"open {path}");
                ReadFromFileOutput result;
                using (StreamReader fileStream = File.OpenText(path)) {
                    var name = Path.GetFileName(path);
                    var content = await fileStream.ReadToEndAsync();
                    result = new ReadFromFileOutput(name, content);
                }

                return result;
            },
            readFromFileBlockOptions);

        var generateTestFileBlock = new TransformBlock<ReadFromFileOutput, GenerateTestFileOutput>(input => {
                var result = generator.Generate(input.Content);
                return new GenerateTestFileOutput(input.Name, result);
            },
            generateTestFileOptions);

        var writeToFileBlock = new ActionBlock<GenerateTestFileOutput>(async input => {
                if (!input.Content.Any()) {
                    return;
                }

                Console.WriteLine($"write {input.Name}");
                using FileStream fileStream = File.Create(output + $"\\{input.Name}");
                byte[] info = new UTF8Encoding(true).GetBytes(input.Content);
                await fileStream.WriteAsync(info);
            },
            writeToFileBlockOptions);

        var linkOptions = new DataflowLinkOptions { PropagateCompletion = true };

        readFromFileBlock.LinkTo(generateTestFileBlock, linkOptions);
        generateTestFileBlock.LinkTo(writeToFileBlock, linkOptions);

        foreach (var file in input) {
            await readFromFileBlock.SendAsync(file);
        }

        readFromFileBlock.Complete();

        writeToFileBlock.Completion.Wait();
    }
}