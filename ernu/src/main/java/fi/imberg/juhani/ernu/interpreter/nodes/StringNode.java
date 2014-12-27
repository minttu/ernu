package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Appendable;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.interfaces.Printable;
import fi.imberg.juhani.ernu.interpreter.interfaces.Sequence;

public class StringNode implements Node, Comparable<StringNode>, Sequence, Printable, Appendable {
    private final String stringLiteral;

    public StringNode() {
        this.stringLiteral = "";
    }

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
    public Node newEmpty() {
        return new StringNode();
    }

    @Override
    public String toPrintable() {
        return stringLiteral;
    }


    @Override
    public Node append(Node node) {
        String other;
        if (node instanceof Printable) {
            other = ((Printable) node).toPrintable();
        } else {
            other = node.toString();
        }
        return new StringNode(stringLiteral + other);
    }

    @Override
    public Node prepend(Node node) {
        String other;
        if (node instanceof Printable) {
            other = ((Printable) node).toPrintable();
        } else {
            other = node.toString();
        }
        return new StringNode(other + stringLiteral);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StringNode that = (StringNode) o;

        return getStringLiteral().equals(that.getStringLiteral());
    }
}
