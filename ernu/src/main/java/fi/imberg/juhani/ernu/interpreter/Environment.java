package fi.imberg.juhani.ernu.interpreter;

import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.exceptions.UnknownAttributeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.BooleanNode;
import fi.imberg.juhani.ernu.interpreter.nodes.StringNode;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final HashMap<String, Node> symbols;
    private final String fileName;
    private Environment parent;
    private boolean executed;

    public Environment(boolean executed, Environment parent, String fileName) {
        this.symbols = new HashMap<>();
        this.parent = parent;
        this.fileName = fileName;
        this.executed = executed;
        if (parent == null && !fileName.equals("builtin")) {
            this.parent = new BuiltinEnvironment();
        }
    }

    public Environment(Environment parent, String fileName) {
        this(parent == null, parent, fileName);
    }

    public Environment(String fileName) {
        this(null, fileName);
    }

    public void addSymbol(String string, Node node) {
        this.symbols.put(string, node);
    }

    public Node getSymbol(String string) throws RuntimeException {
        // These are environment specific so they can't be passed down.
        switch (string) {
            case "__name__":
                return new StringNode(fileName);
            case "__executed__":
                return new BooleanNode(executed);
        }

        Node node = symbols.get(string);

        if (node == null) {
            if (parent == null) {
                throw new UnknownAttributeException(string);
            } else {
                return parent.getSymbol(string);
            }
        }
        return node;
    }

    public Environment subEnvironment() {
        return new Environment(false, this, this.fileName);
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

    public Map<String, Node> getSymbols() {
        return this.symbols;
    }
}
