package fi.imberg.juhani.ernu.parser.node;

import java.util.List;

public class CallNode implements Node {
    private final Node what;
    private final List<Node> arguments;

    public CallNode(Node what, List<Node> arguments) {
        this.what = what;
        this.arguments = arguments;
    }

    public Node getWhat() {
        return what;
    }

    public List<Node> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "(" + what + " " + arguments + ")";
    }
}
