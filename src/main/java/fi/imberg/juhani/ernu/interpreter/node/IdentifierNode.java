package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

public class IdentifierNode implements Node {
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
        return environment.getSymbol(name);
    }

    public void setValue(Environment environment, Node value) throws RuntimeException {
        environment.addSymbol(name, value);
    }
}
