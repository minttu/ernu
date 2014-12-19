package fi.imberg.juhani.ernu.interpreter;

import fi.imberg.juhani.ernu.interpreter.builtin.PrintFunction;
import fi.imberg.juhani.ernu.interpreter.builtin.RangeFunction;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.node.Node;

import java.util.HashMap;

public class Environment {
    private final HashMap<String, Node> symbols;
    private Environment parent;

    public Environment(Environment parent) {
        this.symbols = new HashMap<>();
        this.parent = parent;
        addSymbol("print", new PrintFunction());
        addSymbol("range", new RangeFunction());
    }

    public Environment() {
        this(null);
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
        Environment environment = new Environment(this);
        return environment;
    }
}
