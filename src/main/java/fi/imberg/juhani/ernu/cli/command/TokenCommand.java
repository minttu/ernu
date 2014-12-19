package fi.imberg.juhani.ernu.cli.command;

import fi.imberg.juhani.ernu.cli.exceptions.CLIException;
import fi.imberg.juhani.ernu.cli.exceptions.FileNotFoundException;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.Tokenizer;
import fi.imberg.juhani.ernu.util.Range;

public class TokenCommand extends FileCommand {
    @Override
    public void call(String[] args) throws CLIException {
        Tokenizer tokenizer = new Tokenizer();
        for (String fileName : args) {
            handleFile(tokenizer, fileName);
        }
    }

    public void handleFile(Tokenizer tokenizer, String fileName) throws FileNotFoundException {
        tokenizer.tokenize(fileToString(fileName) + "\n\n");
        while (!tokenizer.isEmpty()) {
            Token token = tokenizer.nextToken();
            if (token != null) {
                System.out.println(token);
            }
        }
    }

    @Override
    public Range getRange() {
        return new Range(1);
    }

    @Override
    public String getCommand() {
        return "tokens";
    }

    @Override
    public String getDescription() {
        return "Prints the tokens used in a file.";
    }
}
