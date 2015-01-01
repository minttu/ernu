package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Internal node for getting a singular value out of several statements.
 */
public class BlockNode implements Node {
    /**
     * List of the nodes inside of this
     */
    private final List<Node> nodes;

    /**
     * @param nodes List of the nodes inside of this
     */
    public BlockNode(List<Node> nodes) {
        while (nodes.contains(null)) {
            nodes.remove(null);
        }
        this.nodes = nodes;
    }

    public BlockNode() {
        this.nodes = new ArrayList<>();
    }

    @Override
    public String toString() {
        return nodes.toString();
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (i == nodes.size() - 1) {
                return node.getValue(environment);
            } else {
                node.getValue(environment);
            }
        }

        return new NullNode();
    }
}
