package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Identifier;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;

/**
 * Identifier nodes are just basic labels that can be used for assignment and stuff.
 */
public class IdentifierNode implements Node, Identifier {
    private final String name;

    public IdentifierNode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        Node value = environment.getSymbol(name);
        if (value instanceof IdentifierNode) {
            value = value.getValue(environment);
        }
        return value;
    }

    @Override
    public void setValue(Environment environment, Node value) throws RuntimeException {
        environment.addSymbol(name, value);
    }
}
