package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.ReturnException;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;

/**
 * Return nodes throw ReturnExceptions to return values to functions.
 */
public class ReturnNode implements Node {
    /**
     * The value this return returns
     */
    private final Node value;

    /**
     * @param value The value this return returns
     */
    public ReturnNode(Node value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "(return " + value + ")";
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        throw new ReturnException(value.getValue(environment));
    }
}
