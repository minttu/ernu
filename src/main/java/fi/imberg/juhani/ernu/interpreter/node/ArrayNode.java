package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

import java.util.List;

public class ArrayNode implements Node {
    private final List<Node> values;

    public ArrayNode(List<Node> values) {
        this.values = values;
    }

    public void set(int index, Node value) {
        while (values.size() <= index) {
            values.add(new NullNode());
        }
        values.set(index, value);
    }

    public Node get(int index) throws RuntimeException {
        if (values.size() <= index) {
            throw new RuntimeException("No such element in the array.");
        }
        return values.get(index);
    }

    public int size() {
        return values.size();
    }

    @Override
    public String toString() {
        return values.toString();
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        return this;
    }
}
