package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.FunctionNode;
import fi.imberg.juhani.ernu.interpreter.nodes.StringNode;

import java.util.List;

public class HelpFunction extends BuiltinFunction {
    public HelpFunction() {
        super("Helps with a function.");
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        if (arguments.size() != 1) {
            throw new RuntimeException("help takes one argument only");
        }
        Node node = arguments.get(0).getValue(environment);
        ;
        if (!(node instanceof FunctionNode)) {
            throw new RuntimeException("helps argument must be a function");
        }
        return new StringNode(((FunctionNode) node).getDoc());
    }
}
