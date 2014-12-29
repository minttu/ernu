package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.ArrayNode;
import fi.imberg.juhani.ernu.interpreter.nodes.StringNode;

import java.util.List;

/**
 * Creates a string out of a node.
 */
public class StrFunction extends BuiltinFunction {
    public StrFunction() {
        super("Returns a formatted representation.");
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        if (arguments.size() != 1) {
            throw new RuntimeException("str takes one argument only");
        }
        Node node = arguments.get(0).getValue(environment);
        if (node instanceof StringNode) {
            return node;
        }
        if (node instanceof ArrayNode) {
            StringBuilder sb = new StringBuilder();
            for (Node subnode : ((ArrayNode) node).getValues()) {
                if (subnode instanceof StringNode) {
                    sb.append(((StringNode) subnode).getStringLiteral());
                } else {
                    sb.append(sb.toString());
                }
            }
            return new StringNode(sb.toString());
        }
        return new StringNode(node.toString());
    }
}
