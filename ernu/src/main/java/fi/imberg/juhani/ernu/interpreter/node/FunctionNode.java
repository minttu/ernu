package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

import java.util.List;

public class FunctionNode implements Node {
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
        Environment local = environment; //.subEnvironment();

        if (arguments.size() != this.arguments.size()) {
            throw new RuntimeException("Wrong number of arguments");
        }
        for (int i = 0; i < this.arguments.size(); i++) {
            Node identifier = this.arguments.get(i);
            Node value = arguments.get(i);
            if (!(identifier instanceof IdentifierNode)) {
                throw new RuntimeException("Non identifier node in arguments");
            }
            ((IdentifierNode) identifier).setValue(local, value);
        }
        return body.getValue(environment);
    }
}
