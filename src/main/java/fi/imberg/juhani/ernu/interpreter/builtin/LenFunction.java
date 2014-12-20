package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.Length;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.node.*;

import java.util.ArrayList;
import java.util.List;

public class LenFunction extends FunctionNode {
    public LenFunction() {
        super(new ArrayList<Node>(),
                "Returns the length of something.",
                new BlockNode(new ArrayList<Node>()));
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        if(arguments.size() != 1) {
            throw new RuntimeException("len takes exactly one argument");
        }
        Node arg = arguments.get(0);
        if(!(arg instanceof Length)) {
            throw new RuntimeException("len argument should implement len");
        }
        return new IntegerNode(((Length) arg).length());
    }
}