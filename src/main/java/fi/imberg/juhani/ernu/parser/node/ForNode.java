package fi.imberg.juhani.ernu.parser.node;

public class ForNode implements Node {
    private final Node identifier;
    private final Node generator;
    private final Node body;

    public ForNode(Node identifier, Node generator, Node body) {
        this.identifier = identifier;
        this.generator = generator;
        this.body = body;
    }

    public Node getIdentifier() {
        return identifier;
    }

    public Node getGenerator() {
        return generator;
    }

    public Node getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "(for " + identifier + " -> " + generator + " " + body + ")";
    }
}
