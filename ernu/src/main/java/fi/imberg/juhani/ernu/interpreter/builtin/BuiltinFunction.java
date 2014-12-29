package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.BlockNode;
import fi.imberg.juhani.ernu.interpreter.nodes.FunctionNode;
import fi.imberg.juhani.ernu.interpreter.nodes.NullNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Superclass for other builtin functions to extend.
 */
public class BuiltinFunction extends FunctionNode {
    private final BuiltinFunctionCall callFunction;

    public BuiltinFunction(String doc) {
        this(doc, null);
    }

    public BuiltinFunction(String doc, BuiltinFunctionCall callFunction) {
        super(new ArrayList<Node>(),
                doc,
                new BlockNode(new ArrayList<Node>()));
        this.callFunction = callFunction;
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        if (callFunction == null) {
            return new NullNode();
        }
        return callFunction.call(environment, arguments);
    }
}
