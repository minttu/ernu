package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.*;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Tokenizer;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportFunction extends BuiltinFunction {
    public ImportFunction() {
        super("Brings symbols from another file.");
    }

    private String realFilename(Environment environment, String fileName) {
        if (!fileName.contains(".ernu")) {
            return fileName;
        }
        String[] parts = environment.getFileName().split("/");
        parts[parts.length - 1] = fileName;
        fileName = "";
        for (String string : parts) {
            fileName += string + '/';
        }
        return fileName.substring(0, fileName.length() - 1);
    }

    private String fileToString(String fileName) throws RuntimeException {
        if (!(fileName.contains(".ernu"))) {
            return loadLib(fileName);
        }
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(Paths.get(fileName));
        } catch (IOException ex) {
            throw new RuntimeException("No such file as \"" + fileName + "\".");
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private String loadLib(String fileName) throws RuntimeException {
        try {
            URI uri = this.getClass().getResource("/" + fileName + ".ernu").toURI();

            // java7 <3 http://stackoverflow.com/a/22605905
            Map<String, String> env = new HashMap<>();
            String[] array = uri.toString().split("!");
            FileSystem fs = FileSystems.newFileSystem(URI.create(array[0]), env);
            Path path = fs.getPath(array[1]);
            String result = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            fs.close();

            return result;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("No such library as \"" + fileName + "\".");
        }
    }

    private Node processFile(Environment environment, String fileName) throws RuntimeException {
        fileName = realFilename(environment, fileName);
        String source = fileToString(fileName);

        Environment other = new Environment(fileName);

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
            if(node != null) {
                node.getValue(other);
            }
        }
        return new EnvironmentNode(other);
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        if (arguments.size() != 1) {
            throw new RuntimeException("import takes exactly one argument");
        }
        Node arg = arguments.get(0).getValue(environment);
        return processFile(environment, ((StringNode) arg).getStringLiteral());
    }
}
