package fi.imberg.juhani.ernu.parser.node;

public class WhileNode implements Node {
    private final Node conditional;
    private final Node body;

    public WhileNode(Node conditional, Node body) {
        this.conditional = conditional;
        this.body = body;
    }

    public Node getConditional() {
        return conditional;
    }

    public Node getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "(while " + conditional + " " + body + ")";
    }
}
