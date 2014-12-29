package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Callable;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * A node that calls a callable with arguments given to it.
 */
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
        Environment env = environment;
        Node node = what.getValue(environment);
        if (!(node instanceof Callable)) {
            throw new RuntimeException("Not callable. Got: " + node);
        }
        List<Node> args = new ArrayList<>();
        if (what instanceof ObjectAccessNode) {
            ObjectAccessNode objectAccessNode = (ObjectAccessNode) what;
            Node object = objectAccessNode.getObject().getValue(environment);
            if (object instanceof ObjectNode) {
                args.add(((ObjectAccessNode) what).getObject());
            } else if (object instanceof EnvironmentNode) {
                env = ((EnvironmentNode) object).getEnvironment();
            }
        }
        for (Node value : arguments) {
            args.add(value.getValue(environment));
        }
        return ((Callable) node).call(env, args);
    }
}
