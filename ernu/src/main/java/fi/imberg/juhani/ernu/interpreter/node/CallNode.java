package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

import java.util.ArrayList;
import java.util.List;

public class CallNode implements Node {
    private final Node what;
    private final List<Node> arguments;

    public CallNode(Node what, List<Node> arguments) {
        this.what = what;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "(" + what + " " + arguments + ")";
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        if (!(what instanceof IdentifierNode)) {
            throw new RuntimeException("Not callable.");
        }
        Node node = what.getValue(environment);
        if (!(node instanceof FunctionNode)) {
            throw new RuntimeException("Not callable.");
        }
        List<Node> args = new ArrayList<>();
        for (Node value : arguments) {
            args.add(value.getValue(environment));
        }
        return ((FunctionNode) node).call(environment, args);
    }
}
