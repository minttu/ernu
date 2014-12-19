package fi.imberg.juhani.ernu.cli.command;

import fi.imberg.juhani.ernu.ErnuException;
import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Tokenizer;
import fi.imberg.juhani.ernu.util.Range;

public class RunCommand extends FileCommand {
    @Override
    public void call(String[] args) throws ErnuException {
        Tokenizer tokenizer = new Tokenizer();
        Parser parser = new Parser(tokenizer);
        for (String fileName : args) {
            handleFile(parser, tokenizer, fileName);
        }
    }

    public void handleFile(Parser parser, Tokenizer tokenizer, String fileName) throws ErnuException {
        tokenizer.tokenize(fileToString(fileName) + "\n\n");
        Environment environment = new Environment();
        while (!tokenizer.isEmpty()) {
            Node node = parser.parseNode();
            if (node != null) {
                node.getValue(environment);
            }
        }
    }

    @Override
    public Range getRange() {
        return new Range(1);
    }

    @Override
    public String getCommand() {
        return "run";
    }

    @Override
    public String getDescription() {
        return "Runs the program.";
    }
}
