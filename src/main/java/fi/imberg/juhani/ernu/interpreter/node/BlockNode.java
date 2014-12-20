package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

import java.util.List;

public class BlockNode implements Node {
    private final List<Node> nodes;

    public BlockNode(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return nodes.toString();
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        Environment local = environment;
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (node == null) {
                continue;
            }

            if (node instanceof ReturnNode || i == nodes.size() - 1) {
                return node.getValue(local);
            } else {
                node.getValue(local);
            }
        }

        return new NullNode();
    }
}
