package fi.imberg.juhani.ernu.lang.node;

import java.util.List;

public class FunctionNode implements Node {
    private final List<Node> arguments;
    private final Node structure;

    public FunctionNode(List<Node> arguments, Node structure) {
        this.arguments = arguments;
        this.structure = structure;
    }

    public List<Node> getArguments() {
        return arguments;
    }

    public Node getStructure() {
        return structure;
    }

    @Override
    public String toString() {
        return "(fn " + arguments + " " + structure + ")";
    }
}
