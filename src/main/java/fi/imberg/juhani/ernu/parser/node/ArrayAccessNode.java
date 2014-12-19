package fi.imberg.juhani.ernu.parser.node;

public class ArrayAccessNode implements Node {
    private final Node identifier;
    private final Node index;

    public ArrayAccessNode(Node identifier, Node index) {
        this.identifier = identifier;
        this.index = index;
    }

    @Override
    public String toString() {
        return identifier + "[" + index + "]";
    }
}
