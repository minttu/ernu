package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.*;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.interpreter.node.NumberNode;
import fi.imberg.juhani.ernu.interpreter.node.StringNode;

import java.util.List;

public class NumFunction extends BuiltinFunction {
    public NumFunction() {
        super("Converts a string to number");
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        if (arguments.size() != 1) {
            throw new RuntimeException("num takes one argument only");
        }
        Node node = arguments.get(0).getValue(environment);
        if(!(node instanceof StringNode)) {
            throw new RuntimeException("num takes a string only");
        }
        return new NumberNode(((StringNode) node).getStringLiteral());
    }
}
