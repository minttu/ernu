package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.node.BlockNode;
import fi.imberg.juhani.ernu.interpreter.node.FunctionNode;
import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.interpreter.node.NullNode;

import java.util.ArrayList;
import java.util.List;

public class PrintFunction extends FunctionNode {
    public PrintFunction() {
        super(new ArrayList<Node>(),
                "Prints stuff.",
                new BlockNode(new ArrayList<Node>()));
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        String string = arguments.toString();
        System.out.println(string.substring(1, string.length() - 1));
        return new NullNode();
    }
}
