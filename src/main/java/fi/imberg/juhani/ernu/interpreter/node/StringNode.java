package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

public class StringNode implements Node {
    private final String stringLiteral;

    public StringNode(String stringLiteral) {
        this.stringLiteral = stringLiteral.substring(1, stringLiteral.length() - 1);
    }

    public String getStringLiteral() {
        return stringLiteral;
    }

    @Override
    public String toString() {
        return "\"" + stringLiteral + "\"";
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        return this;
    }
}
