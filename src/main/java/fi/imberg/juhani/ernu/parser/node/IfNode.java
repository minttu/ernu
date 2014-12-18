package fi.imberg.juhani.ernu.parser.node;

public class IfNode implements Node {
    private final Node conditional;
    private final Node trueBranch;
    private final Node falseBranch;

    public IfNode(Node conditional, Node trueBranch, Node falseBranch) {
        this.conditional = conditional;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    public Node getConditional() {
        return conditional;
    }

    public Node getTrueBranch() {
        return trueBranch;
    }

    public Node getFalseBranch() {
        return falseBranch;
    }

    @Override
    public String toString() {
        return "(if " + conditional + " " + trueBranch + " " + falseBranch + ")";
    }
}
