package fi.imberg.juhani.ernu.parser.node;

import java.util.List;

public class NewArrayNode implements Node {
    private final List<Node> initial;

    public NewArrayNode(List<Node> initial) {
        this.initial = initial;
    }

    @Override
    public String toString() {
        return initial.toString();
    }
}
