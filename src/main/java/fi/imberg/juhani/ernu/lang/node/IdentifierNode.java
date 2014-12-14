package fi.imberg.juhani.ernu.lang.node;

public class IdentifierNode implements Node {
    private final String name;

    public IdentifierNode(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
