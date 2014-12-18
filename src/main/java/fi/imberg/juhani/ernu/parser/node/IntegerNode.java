package fi.imberg.juhani.ernu.parser.node;

public class IntegerNode implements Node {
    private final int value;

    public IntegerNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
