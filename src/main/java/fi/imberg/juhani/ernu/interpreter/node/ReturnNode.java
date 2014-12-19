package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

public class ReturnNode implements Node {
    private final Node value;

    public ReturnNode(Node value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "(return " + value + ")";
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        return value.getValue(environment);
    }
}
