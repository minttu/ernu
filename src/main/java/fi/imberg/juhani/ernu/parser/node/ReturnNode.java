package fi.imberg.juhani.ernu.parser.node;

public class ReturnNode implements Node {
    private final Node value;

    public ReturnNode(Node value) {
        this.value = value;
    }

    public Node getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "(return " + value + ")";
    }
}
