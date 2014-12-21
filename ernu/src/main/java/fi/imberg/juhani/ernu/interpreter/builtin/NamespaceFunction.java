package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.interpreter.node.NullNode;
import fi.imberg.juhani.ernu.interpreter.node.StringNode;

import java.util.List;

public class NamespaceFunction extends BuiltinFunction {
    public NamespaceFunction() {
        super("Switches namespaces");
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        if (arguments.size() != 1) {
            throw new RuntimeException("namespace takes one argument only");
        }
        Node node = arguments.get(0).getValue(environment);;
        if (!(node instanceof StringNode)) {
            throw new RuntimeException("namespaces argument must be a string");
        }
        environment.setNamespace(((StringNode) node).getStringLiteral());
        return new NullNode();
    }
}
