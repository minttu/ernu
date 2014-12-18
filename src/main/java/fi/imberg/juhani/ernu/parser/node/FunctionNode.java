package fi.imberg.juhani.ernu.parser.node;

import java.util.List;

public class FunctionNode implements Node {
    private final List<Node> arguments;
    private final Node body;
    private final String doc;

    public FunctionNode(List<Node> arguments, String doc, Node body) {
        this.arguments = arguments;
        this.body = body;
        this.doc = doc;
    }

    public List<Node> getArguments() {
        return arguments;
    }

    public Node getBody() {
        return body;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(fn");
        if(doc.length() > 0) {
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
}
