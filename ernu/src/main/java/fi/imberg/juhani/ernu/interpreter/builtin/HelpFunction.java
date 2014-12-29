package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.interfaces.Object;

import java.util.List;

/**
 * Returns the __doc__ attribute of an object.
 */
public class HelpFunction extends BuiltinFunction {
    public HelpFunction() {
        super("Helps with something.");
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        if (arguments.size() != 1) {
            throw new RuntimeException("help takes one argument only");
        }
        Node node = arguments.get(0).getValue(environment);

        if (!(node instanceof Object)) {
            throw new RuntimeException("helps argument must be an object");
        }
        return ((Object) node).getAttribute("__doc__");
    }
}
