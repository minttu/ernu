package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.parser.ErnuParser;
import fi.imberg.juhani.ernu.parser.Tokenizer;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;

/**
 * Import nodes hold information about a import. They bring symbols from a file to another one.
 */
public class ImportNode implements Node {
    private final String what;
    private final String as;
    private final List<String> subs;
    private FileSystem fs;

    public ImportNode(String what, String as, List<String> subs) {
        this.what = what;
        this.as = as;
        this.subs = subs;
    }

    private Path createLocalFilePath(Environment environment, String name) {
        String[] parts = environment.getFileName().split("/");
        parts[parts.length - 1] = name;
        name = "";
        for (String string : parts) {
            name += string + '/';
        }
        return Paths.get(name.substring(0, name.length() - 1) + ".ernu");
    }

    private Path createLibraryFilePath(String name) {
        URI uri = null;
        try {
            uri = this.getClass().getResource("/" + name + ".ernu").toURI();
        } catch (URISyntaxException | NullPointerException e) {
            return null;
        }

        // So, it seems when I try to mvn test this, there is no jar so the path is a real path in the FS.
        if (!uri.toString().contains("!")) {
            return Paths.get(uri);
        }

        String[] array = uri.toString().split("!");
        fs = null;
        Path path = null;
        try {
            fs = FileSystems.newFileSystem(URI.create(array[0]), new HashMap<>());
            path = fs.getPath(array[1]);
        } catch (IOException e) {
            return null;
        }
        return path;
    }

    private Path resolvePath(Environment environment, String name) {
        name = name.replaceAll("\\.", "\\/");
        Path local = createLocalFilePath(environment, name);
        if (local != null && Files.exists(local)) {
            return local;
        }
        Path library = createLibraryFilePath(name);
        if (library != null && Files.exists(library)) {
            return library;
        }
        return null;
    }

    private String loadPath(Path path) throws RuntimeException {
        if (path == null) {
            throw new RuntimeException("No such file as " + what.replaceAll("\\.", "\\/"));
        }
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("No such file as " + path);
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private EnvironmentNode createEnvironment(Path name, String source) throws RuntimeException {
        Environment other = new Environment(false, null, name.toString());

        Tokenizer tokenizer = new Tokenizer();
        ErnuParser parser = new ErnuParser(tokenizer);
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
        return new EnvironmentNode(other);
    }

    private String getLastPart() {
        if (as.length() > 0) {
            return as;
        }
        String[] parts = what.split("\\.");
        return parts[parts.length - 1];
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        Path path = resolvePath(environment, what);
        EnvironmentNode node = createEnvironment(path, loadPath(path));
        if (subs.size() == 0) {
            environment.addSymbol(getLastPart(), node);
        } else {
            for (String sub : subs) {
                environment.addSymbol(sub, node.getProxyTo(sub));
            }
        }
        try {
            fs.close();
        } catch (IOException | NullPointerException ignored) {

        }
        return new NullNode();
    }

    @Override
    public String toString() {
        if (subs.size() == 0) {
            if (!what.equals(getLastPart())) {
                return "(import " + what + " as " + getLastPart() + ")";
            } else {
                return "(import " + what + ")";
            }
        } else {
            return "(from " + what + " import " + subs + ")";
        }

    }

}
