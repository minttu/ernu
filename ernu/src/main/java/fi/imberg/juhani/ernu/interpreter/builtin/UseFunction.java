package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.NullNode;
import fi.imberg.juhani.ernu.interpreter.nodes.StringNode;

import java.util.List;

public class UseFunction extends BuiltinFunction {
    public UseFunction() {
        super("Allows writing a instead of b.a");
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        for (Node node : arguments) {
            environment.addUsing(((StringNode) node.getValue(environment)).getStringLiteral());
        }
        return new NullNode();
    }
}
