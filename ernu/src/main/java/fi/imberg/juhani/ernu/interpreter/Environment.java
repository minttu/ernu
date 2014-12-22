package fi.imberg.juhani.ernu.interpreter;

import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.node.BooleanNode;
import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.interpreter.node.StringNode;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final HashMap<String, Node> symbols;
    private final String fileName;
    private Environment parent;
    private boolean executed;
    private String namespace;

    public Environment(Environment parent, String fileName) {
        this.symbols = new HashMap<>();
        this.parent = parent;
        this.fileName = fileName;
        this.executed = parent == null;
        this.namespace = "";
        if (executed && !fileName.equals("builtin")) {
            this.parent = new BuiltinEnvironment();
        }
    }

    public Environment(String fileName) {
        this(null, fileName);
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
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

        String targetNS = "";
        String targetID = "";
        for (char c : string.toCharArray()) {
            if (c == '.') {
                targetNS += targetID;
                targetID = "";
            } else {
                targetID += c;
            }
        }
        Node node = null;

        if (namespace.equals(targetNS)) {
            node = symbols.get(targetID);
        }

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
        return new Environment(this, this.fileName);
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

    public Environment findNamespace(String string) {
        if (namespace.equals(string)) {
            return this;
        } else {
            if (parent == null) {
                return null;
            } else {
                return parent.findNamespace(string);
            }
        }
    }

    public void addUsing(String string) throws RuntimeException {
        Environment other = findNamespace(string);
        if (other != null) {
            Map<String, Node> otherSymbols = other.getSymbols();
            symbols.putAll(otherSymbols);
        } else {
            throw new RuntimeException("Unknown namespace");
        }
    }
}
