package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.node.ArrayNode;
import fi.imberg.juhani.ernu.interpreter.node.NumberNode;
import fi.imberg.juhani.ernu.interpreter.node.Node;

import java.util.ArrayList;
import java.util.List;

public class RangeFunction extends BuiltinFunction {
    public RangeFunction() {
        super("Creates a range.");
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        List<Node> values = new ArrayList<>();
        int to = ((NumberNode) arguments.get(0)).getInteger();
        int from = 0;
        if (arguments.size() == 2) {
            from = ((NumberNode) arguments.get(1)).getInteger();
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
