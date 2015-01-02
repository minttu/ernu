package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.StringNode;

import java.util.List;

/**
 * Returns the nodes type as a string.
 */
public class TypeFunction extends BuiltinFunction {
    public TypeFunction() {
        super("Returns the type of something");
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        if (arguments.size() != 1) {
            throw new RuntimeException("type takes one argument only");
        }
        Node node = arguments.get(0);
        return new StringNode(node.getClass().getSimpleName());
    }
}
