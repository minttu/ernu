package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.ArrayNode;
import fi.imberg.juhani.ernu.interpreter.nodes.NumberNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Returns a list of numbers in an increasing order.
 */
public class RangeFunction extends BuiltinFunction {
    public RangeFunction() {
        super("Creates a range.");
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        if (arguments.size() == 0) {
            throw new RuntimeException("range takes at least one argument");
        }
        if (arguments.size() > 3) {
            throw new RuntimeException("range takes three arguments at most");
        }
        List<NumberNode> args = new ArrayList<>();
        for (Node node : arguments) {
            Node v = node.getValue(environment);
            if (!(v instanceof NumberNode)) {
                throw new RuntimeException("range takes number nodes as arguments");
            }
            args.add((NumberNode) v);
        }

        List<Node> values = new ArrayList<>();
        int to = args.get(0).getInteger();
        int from = 0;
        int step = 1;
        if (args.size() >= 2) {
            from = args.get(1).getInteger();
            int asd = to;
            to = from;
            from = asd;
        }
        if (args.size() == 3) {
            step = args.get(2).getInteger();
        }
        if (step > 0) {
            for (int i = from; i < to; i += step) {
                values.add(new NumberNode(i));
            }
        } else {
            for (int i = to; i > from; i += step) {
                values.add(new NumberNode(i));
            }
        }
        return new ArrayNode(values);
    }
}
