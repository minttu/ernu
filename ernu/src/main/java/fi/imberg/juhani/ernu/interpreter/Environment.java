package fi.imberg.juhani.ernu.interpreter;

import fi.imberg.juhani.ernu.interpreter.exceptions.UnknownAttributeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.BooleanNode;
import fi.imberg.juhani.ernu.interpreter.nodes.StringNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Describes an environment where nodes are executed.
 */
public class Environment {
    /**
     * This map holds all the symbols in this environment
     */
    private final HashMap<String, Node> symbols;
    /**
     * Which file does this environment originate from
     */
    private final String fileName;
    /**
     * Which environment is this ones parent
     */
    private Environment parent;
    /**
     * Is this environment directly executed
     */
    private boolean executed;

    /**
     * @param executed Is this environment directly executed
     * @param parent   Which environment is this ones parent
     * @param fileName Which file does this environment originate from
     */
    public Environment(boolean executed, Environment parent, String fileName) {
        this.symbols = new HashMap<>();
        this.parent = parent;
        this.fileName = fileName;
        this.executed = executed;
        if (parent == null && !fileName.equals("builtin")) {
            this.parent = new BuiltinEnvironment();
        }
    }

    /**
     * If parent == null marks this as executed
     *
     * @param parent   Which environment is this ones parent
     * @param fileName Which file does this environment originate from
     */
    public Environment(Environment parent, String fileName) {
        this(parent == null, parent, fileName);
    }

    /**
     * The parent is null, and this probably is executed
     *
     * @param fileName Which file does this environment originate from
     */
    public Environment(String fileName) {
        this(null, fileName);
    }

    /**
     * Adds a symbol to the environment.
     *
     * @param string The symbols name
     * @param node   What to add
     */
    public void addSymbol(String string, Node node) {
        this.symbols.put(string, node);
    }

    /**
     * Gets a symbol from the environment or it's parent.
     *
     * @param string The symbols name
     * @return The found node
     * @throws UnknownAttributeException if we can't find the symbol anywhere
     */
    public Node getSymbol(String string) throws UnknownAttributeException {
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

    /**
     * @return An environment which has it's parent set to this one.
     */
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
