package fi.imberg.juhani.ernu.interpreter;

import fi.imberg.juhani.ernu.interpreter.builtin.*;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.node.BooleanNode;
import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.interpreter.node.StringNode;

import java.util.HashMap;

public class Environment {
    private final HashMap<String, Node> symbols;
    private final String fileName;
    private Environment parent;
    private boolean executed;

    public Environment(Environment parent, String fileName) {
        this.symbols = new HashMap<>();
        this.parent = parent;
        this.fileName = fileName;
        this.executed = parent == null;
        addSymbol("print", new PrintFunction());
        addSymbol("range", new RangeFunction());
        addSymbol("import", new ImportFunction());
        addSymbol("len", new LenFunction());
        addSymbol("help", new HelpFunction());
        addSymbol("type", new TypeFunction());
    }

    public Environment(String fileName) {
        this(null, fileName);
    }

    public void addSymbol(String string, Node node) {
        this.symbols.put(string, node);
    }

    public Node getSymbol(String string) throws RuntimeException {
        switch (string) {
            case "__name__":
                return new StringNode(fileName);
            case "__executed__":
                return new BooleanNode(executed);
        }
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
