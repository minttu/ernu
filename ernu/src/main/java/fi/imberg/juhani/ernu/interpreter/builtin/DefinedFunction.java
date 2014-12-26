package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.BooleanNode;
import fi.imberg.juhani.ernu.interpreter.nodes.StringNode;

import java.util.List;

public class DefinedFunction extends BuiltinFunction {
    public DefinedFunction() {
        super("Returns true if something is defined");
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        if (arguments.size() != 1) {
            throw new RuntimeException("defined takes one argument only");
        }
        Node node = arguments.get(0).getValue(environment);
        ;
        if (!(node instanceof StringNode)) {
            throw new RuntimeException("defineds argument must be a string nodes");
        }
        try {
            environment.getSymbol(((StringNode) node).getStringLiteral());
        } catch (RuntimeException e) {
            return new BooleanNode(false);
        }
        return new BooleanNode(true);
    }
}
