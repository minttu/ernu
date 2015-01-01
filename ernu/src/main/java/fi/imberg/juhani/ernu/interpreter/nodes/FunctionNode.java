package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.ImmutableException;
import fi.imberg.juhani.ernu.interpreter.exceptions.ReturnException;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.exceptions.UnknownAttributeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Callable;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.interfaces.Object;

import java.util.List;

/**
 * A function node holds a function, the arguments it takes and the statements that construct the functions body.
 */
public class FunctionNode implements Node, Callable, Object {
    /**
     * A list of identifiers that all get assigned values from the call
     */
    private final List<Node> arguments;
    /**
     * The body of this function
     */
    private final BlockNode body;
    /**
     * The documentation of this function
     */
    private final String doc;

    /**
     * @param arguments A list of identifiers that all get assigned values from the call
     * @param doc       The body of this function
     * @param body      The documentation of this function
     */
    public FunctionNode(List<Node> arguments, String doc, BlockNode body) {
        this.arguments = arguments;
        this.body = body;
        this.doc = doc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(fn");
        if (doc.length() > 0) {
            sb.append(" \"");
            sb.append(doc);
            sb.append("\"");
        }
        sb.append(" ");
        sb.append(arguments);
        sb.append(" ");
        sb.append(body);
        sb.append(")");
        return sb.toString();
    }

    @Override
    public Node getValue(Environment environment) {
        return this;
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        Environment local = environment.subEnvironment();

        if (arguments.size() != this.arguments.size()) {
            throw new RuntimeException("Wrong number of arguments");
        }
        for (int i = 0; i < this.arguments.size(); i++) {
            Node identifier = this.arguments.get(i);
            Node value = arguments.get(i);
            if (!(identifier instanceof IdentifierNode)) {
                throw new RuntimeException("Non identifier nodes in arguments");
            }
            ((IdentifierNode) identifier).setValue(local, value);
        }
        try {
            return body.getValue(local);
        } catch (ReturnException ret) {
            return ret.getValue();
        }
    }

    @Override
    public void setAttribute(String key, Node value) throws RuntimeException {
        throw new ImmutableException();
    }

    @Override
    public Node getAttribute(String key) throws RuntimeException {
        switch (key) {
            case "__doc__":
                return new StringNode(doc);
        }
        throw new UnknownAttributeException(key);
    }

    @Override
    public boolean hasAttribute(String key) throws RuntimeException {
        try {
            getAttribute(key);
        } catch (UnknownAttributeException ignored) {
            return false;
        }
        return true;
    }
}
