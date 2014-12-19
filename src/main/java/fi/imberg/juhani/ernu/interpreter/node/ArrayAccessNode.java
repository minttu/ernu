package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

public class ArrayAccessNode extends IdentifierNode {
    private final Node identifier;
    private final Node index;

    public ArrayAccessNode(Node identifier, Node index) {
        super(identifier.toString());
        this.identifier = identifier;
        this.index = index;
    }

    @Override
    public String toString() {
        return identifier + "[" + index + "]";
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        Node value = environment.getSymbol(identifier.toString());
        if (!(value instanceof ArrayNode)) {
            throw new RuntimeException("Identifier is not an array.");
        }
        Node realIndex = index.getValue(environment);
        if (!(realIndex instanceof IntegerNode)) {
            throw new RuntimeException("Index must be an integer.");
        }
        return ((ArrayNode) value).get(((IntegerNode) realIndex).getInteger());
    }

    @Override
    public void setValue(Environment environment, Node value) throws RuntimeException {
        Node node = identifier.getValue(environment);
        if (!(node instanceof ArrayNode)) {
            throw new RuntimeException("Identifier is not an array.");
        }
        Node realIndex = index.getValue(environment);
        if (!(realIndex instanceof IntegerNode)) {
            throw new RuntimeException("Index must be an integer.");
        }
        ((ArrayNode) node).set(((IntegerNode) realIndex).getInteger(), value);
    }
}
