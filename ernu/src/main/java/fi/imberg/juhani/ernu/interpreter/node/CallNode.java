package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.interfaces.Callable;
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
        Node node = what.getValue(environment);
        if (!(node instanceof Callable)) {
            throw new RuntimeException("Not callable. Got: " + node);
        }
        List<Node> args = new ArrayList<>();
        for (Node value : arguments) {
            args.add(value.getValue(environment));
        }
        return ((Callable) node).call(environment, args);
    }
}
