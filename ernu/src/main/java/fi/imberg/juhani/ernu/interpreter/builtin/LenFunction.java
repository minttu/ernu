package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.interfaces.Sequence;
import fi.imberg.juhani.ernu.interpreter.nodes.NumberNode;

import java.util.List;

/**
 * Returns the length of a sequence.
 */
public class LenFunction extends BuiltinFunction {
    public LenFunction() {
        super("Returns the length of something.");
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        if (arguments.size() != 1) {
            throw new RuntimeException("len takes exactly one argument");
        }
        Node arg = arguments.get(0).getValue(environment);

        if (!(arg instanceof Sequence)) {
            throw new RuntimeException("lens argument should be a sequence");
        }
        return new NumberNode(((Sequence) arg).length());
    }
}