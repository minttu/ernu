package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.*;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.interpreter.node.StringNode;

import java.util.List;

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
        if(node instanceof StringNode) {
            return node;
        }
        return new StringNode(node.toString());
    }
}
