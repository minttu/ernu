package fi.imberg.juhani.ernu.interpreter;

import fi.imberg.juhani.ernu.interpreter.builtin.ImportFunction;
import fi.imberg.juhani.ernu.interpreter.builtin.PrintFunction;
import fi.imberg.juhani.ernu.interpreter.builtin.RangeFunction;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.node.Node;

import java.util.HashMap;

public class Environment {
    private final HashMap<String, Node> symbols;
    private final String fileName;
    private Environment parent;

    public Environment(Environment parent, String fileName) {
        this.symbols = new HashMap<>();
        this.parent = parent;
        this.fileName = fileName;
        addSymbol("print", new PrintFunction());
        addSymbol("range", new RangeFunction());
        addSymbol("import", new ImportFunction());
    }

    public Environment(String fileName) {
        this(null, fileName);
    }

    public void addSymbol(String string, Node node) {
        this.symbols.put(string, node);
    }

    public Node getSymbol(String string) throws RuntimeException {
        Node node = this.symbols.get(string);
        if (node == null) {
            if (parent == null) {
                throw new RuntimeException("Unknown symbol \"" + string + "\"");
            } else {
                return parent.getSymbol(string);
            }
        }
        return node;
    }

    public Environment subEnvironment() {
        Environment environment = new Environment(this, this.fileName);
        return environment;
    }

    public Environment getParent() {
        return parent;
    }

    public void setParent(Environment parent) {
        this.parent = parent;
    }

    public String getFileName() {
        return fileName;
    }
}
