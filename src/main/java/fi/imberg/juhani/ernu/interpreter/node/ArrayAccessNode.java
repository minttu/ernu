package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.Sequence;
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
        Node value = identifier.getValue(environment);
        if (!(value instanceof Sequence)) {
            throw new RuntimeException("Identifier is not a sequence.");
        }
        Node realIndex = index.getValue(environment);
        if (!(realIndex instanceof IntegerNode)) {
            throw new RuntimeException("Index must be an integer.");
        }
        IntegerNode realIndexInteger = (IntegerNode) realIndex;
        Sequence sequence = (Sequence) value;
        if(realIndexInteger.getInteger() >= sequence.length()) {
            throw new RuntimeException("Array access out of bounds.");
        }
        return sequence.get(realIndexInteger.getInteger());
    }

    @Override
    public void setValue(Environment environment, Node value) throws RuntimeException {
        Node node = identifier.getValue(environment);
        if (!(node instanceof Sequence)) {
            throw new RuntimeException("Identifier is not a sequence.");
        }
        Node realIndex = index.getValue(environment);
        if (!(realIndex instanceof IntegerNode)) {
            throw new RuntimeException("Index must be an integer.");
        }
        IntegerNode realIndexInteger = (IntegerNode) realIndex;
        Sequence sequence = (Sequence) node;
        sequence.set(realIndexInteger.getInteger(), value);
    }
}
