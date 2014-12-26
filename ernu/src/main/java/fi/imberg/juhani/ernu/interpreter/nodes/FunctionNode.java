package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.ImmutableException;
import fi.imberg.juhani.ernu.interpreter.exceptions.ReturnException;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.exceptions.UnknownAttributeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.*;
import fi.imberg.juhani.ernu.interpreter.interfaces.Object;

import java.util.List;

public class FunctionNode implements Node, Callable, Object {
    private final List<Node> arguments;
    private final BlockNode body;
    private final String doc;

    public FunctionNode(List<Node> arguments, String doc, BlockNode body) {
        this.arguments = arguments;
        this.body = body;
        this.doc = doc;
    }

    public String getDoc() {
        return doc;
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
