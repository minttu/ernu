package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.Sequence;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.node.*;

import java.util.List;

public class LenFunction extends BuiltinFunction {
    public LenFunction() {
        super("Returns the length of something.");
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        if(arguments.size() != 1) {
            throw new RuntimeException("len takes exactly one argument");
        }
        Node arg = arguments.get(0);
        if(!(arg instanceof Sequence)) {
            throw new RuntimeException("len argument should implement len");
        }
        return new IntegerNode(((Sequence) arg).length());
    }
}