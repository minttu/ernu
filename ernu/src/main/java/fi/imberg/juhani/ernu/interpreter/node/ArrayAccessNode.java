package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Appendable;
import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.Sequence;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

import java.util.List;

public class ArrayAccessNode extends IdentifierNode {
    private final Node identifier;
    private final List<Node> args;

    public ArrayAccessNode(Node identifier, List<Node> args) {
        super(identifier.toString());
        this.identifier = identifier;
        this.args = args;
    }

    @Override
    public String toString() {
        return identifier + "[" + args + "]";
    }

    private NumberNode getRealIndex(Environment environment) throws RuntimeException {
        Node realIndex = args.get(0).getValue(environment);
        if (!(realIndex instanceof NumberNode)) {
            throw new RuntimeException("Index must be an integer.");
        }
        return (NumberNode) realIndex;
    }

    private Sequence getSequence(Environment environment) throws RuntimeException {
        Node value = identifier.getValue(environment);
        if (!(value instanceof Sequence)) {
            throw new RuntimeException("Identifier is not a sequence.");
        }
        return (Sequence) value;
    }

    private int getActuallyRealPos(Sequence sequence, NumberNode numberNode) throws RuntimeException {
        int pos = numberNode.getInteger();
        if (pos >= 0) {
            if (pos >= sequence.length()) {
                throw new RuntimeException("Array access out of bounds.");
            }
            return pos;
        } else {
            if (Math.abs(pos) - 1 >= sequence.length()) {
                throw new RuntimeException("Array access out of bounds.");
            }
            return sequence.length() - Math.abs(pos);
        }
    }

    private int getIntOfArg(int i, int def, Environment environment) throws RuntimeException {
        if (args.size() <= i) {
            return def;
        }
        Node node = args.get(i).getValue(environment);
        if (node instanceof NullNode) {
            return def;
        }
        if (!(node instanceof NumberNode)) {
            throw new RuntimeException("Index must be an integer.");
        }
        return ((NumberNode) node).getInteger();
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        Sequence sequence = getSequence(environment);
        if (args.size() == 1) {
            NumberNode realIndexInteger = getRealIndex(environment);
            return sequence.get(getActuallyRealPos(sequence, realIndexInteger));
        } else {
            int start = getIntOfArg(0, 0, environment);
            int end = getIntOfArg(1, sequence.length(), environment);
            int step = getIntOfArg(2, 1, environment);
            if (start < 0) {
                start = sequence.length() + start;
            }
            if (end < 0) {
                end = sequence.length() + end;
            }
            Appendable newSequence = (Appendable) sequence.newEmpty();
            if (step >= 0) {
                for (int i = start; i < end; i += step) {
                    newSequence = (Appendable) newSequence.append(sequence.get(i));
                }
            } else {
                for (int i = end - 1; i >= start; i += step) {
                    newSequence = (Appendable) newSequence.append(sequence.get(i));
                }
            }
            return (Node) newSequence;
        }
    }

    @Override
    public void setValue(Environment environment, Node value) throws RuntimeException {
        if (args.size() > 1) {
            throw new RuntimeException("slice assign is not supported");
        }
        NumberNode realIndexInteger = getRealIndex(environment);
        Sequence sequence = getSequence(environment);
        sequence.set(getActuallyRealPos(sequence, realIndexInteger), value);
    }
}
