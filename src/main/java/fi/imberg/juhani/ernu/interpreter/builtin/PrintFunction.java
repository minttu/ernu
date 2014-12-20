package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.Printable;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.node.BlockNode;
import fi.imberg.juhani.ernu.interpreter.node.FunctionNode;
import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.interpreter.node.NullNode;

import java.util.ArrayList;
import java.util.List;

public class PrintFunction extends BuiltinFunction {
    public PrintFunction() {
        super("Prints stuff.");
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        for(int i = 0; i < arguments.size(); i++) {
            Node node = arguments.get(i);
            if(node instanceof Printable) {
                System.out.print(((Printable) node).toPrintable());
            } else {
                System.out.print(node);
            }
            if(i < arguments.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        return new NullNode();
    }
}
