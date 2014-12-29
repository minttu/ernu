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
        List<Node> values = new ArrayList<>();
        int to = ((NumberNode) arguments.get(0).getValue(environment)).getInteger();
        int from = 0;
        if (arguments.size() == 2) {
            from = ((NumberNode) arguments.get(1).getValue(environment)).getInteger();
            int asd = to;
            to = from;
            from = asd;
        }
        for (int i = from; i < to; i++) {
            values.add(new NumberNode(i));
        }
        return new ArrayNode(values);
    }
}
