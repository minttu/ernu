package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.Sequence;
import fi.imberg.juhani.ernu.interpreter.Printable;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

public class StringNode implements Node, Comparable<StringNode>, Sequence, Printable {
    private final String stringLiteral;

    public StringNode(String stringLiteral) {
        this.stringLiteral = stringLiteral;
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

    @Override
    public int compareTo(StringNode other) {
        return getStringLiteral().compareTo(other.getStringLiteral());
    }

    @Override
    public int length() {
        return this.stringLiteral.length();
    }

    @Override
    public Node get(int index) throws RuntimeException {
        return new StringNode("" + stringLiteral.charAt(index));
    }

    @Override
    public void set(int index, Node value) throws RuntimeException {
        throw new RuntimeException("Strings are immutable");
    }

    @Override
    public String toPrintable() {
        return stringLiteral;
    }
}
