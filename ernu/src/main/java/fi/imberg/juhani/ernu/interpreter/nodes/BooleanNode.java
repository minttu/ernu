package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;

/**
 * A node that represents a boolean.
 */
public class BooleanNode implements Node {

    private final boolean truth;

    public BooleanNode(boolean truth) {
        this.truth = truth;
    }

    public boolean isTrue() {
        return truth;
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        return this;
    }

    @Override
    public String toString() {
        return "" + this.truth;
    }
}
