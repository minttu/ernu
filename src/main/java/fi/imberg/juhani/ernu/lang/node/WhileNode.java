package fi.imberg.juhani.ernu.lang.node;

public class WhileNode implements Node {
    private final Node conditional;
    private final Node branch;

    public WhileNode(Node conditional, Node branch) {
        this.conditional = conditional;
        this.branch = branch;
    }

    @Override
    public String toString() {
        return "(while " + conditional + " " + branch + ")";
    }
}
