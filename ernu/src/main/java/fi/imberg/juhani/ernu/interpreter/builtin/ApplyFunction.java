package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.ArrayNode;
import fi.imberg.juhani.ernu.interpreter.nodes.CallNode;
import fi.imberg.juhani.ernu.interpreter.nodes.FunctionNode;

import java.util.List;

public class ApplyFunction extends BuiltinFunction {
    public ApplyFunction() {
        super("Applies a list of arguments to a function");
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        if (arguments.size() != 2) {
            throw new RuntimeException("apply takes two arguments");
        }
        Node node = arguments.get(0).getValue(environment);
        if (!(node instanceof FunctionNode)) {
            throw new RuntimeException("applys first argument must be a function");
        }
        Node args = arguments.get(1).getValue(environment);
        if (!(args instanceof ArrayNode)) {
            throw new RuntimeException("applys second argument must be an array");
        }
        return (new CallNode(node, ((ArrayNode) args).getValues())).getValue(environment);
    }
}
