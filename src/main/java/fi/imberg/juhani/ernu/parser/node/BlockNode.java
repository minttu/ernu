package fi.imberg.juhani.ernu.parser.node;

import java.util.List;

public class BlockNode implements Node {
    private final List<Node> nodes;

    public BlockNode(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        return nodes.toString();
    }

}
