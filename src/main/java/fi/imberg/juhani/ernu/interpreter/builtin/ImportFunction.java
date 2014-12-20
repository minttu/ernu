package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.node.*;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Tokenizer;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImportFunction extends BuiltinFunction {
    public ImportFunction() {
        super("Brings symbols from another file.");
    }

    private String realFilename(Environment environment, String fileName) {
        String[] parts = environment.getFileName().split("/");
        parts[parts.length - 1] = fileName;
        fileName = "";
        for (String string : parts) {
            fileName += string + '/';
        }
        return fileName.substring(0, fileName.length() - 1);
    }

    private String fileToString(String fileName) throws RuntimeException {
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(Paths.get(fileName));
        } catch (IOException ex) {
            throw new RuntimeException("No such file as \"" + fileName + "\".");
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private void processFile(Environment environment, String fileName) throws RuntimeException {
        fileName = realFilename(environment, fileName);
        String source = fileToString(fileName);
        Environment other = new Environment(environment, fileName);

        Environment thisParent = environment.getParent();
        other.setParent(thisParent);
        environment.setParent(other);

        Tokenizer tokenizer = new Tokenizer();
        Parser parser = new Parser(tokenizer);
        tokenizer.tokenize(source + "\n\n");
        while (!tokenizer.isEmpty()) {
            Node node;
            try {
                node = parser.parseNode();
            } catch (LangException e) {
                throw new RuntimeException(e.getMessage());
            }
            if (node != null) {
                node.getValue(other);
            }
        }
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        for (Node node : arguments) {
            processFile(environment, ((StringNode) node).getStringLiteral());
        }
        return new NullNode();
    }
}
