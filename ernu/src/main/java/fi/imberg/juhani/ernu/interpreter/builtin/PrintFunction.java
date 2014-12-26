package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.interfaces.Printable;
import fi.imberg.juhani.ernu.interpreter.nodes.NullNode;

import java.util.List;

public class PrintFunction extends BuiltinFunction {
    public PrintFunction() {
        super("Prints stuff.");
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        for (int i = 0; i < arguments.size(); i++) {
            Node node = arguments.get(i).getValue(environment);
            if (node instanceof Printable) {
                System.out.print(((Printable) node).toPrintable());
            } else {
                System.out.print(node);
            }
            if (i < arguments.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        return new NullNode();
    }
}
