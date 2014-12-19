package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.Math;

public class IntegerNode implements Node, Comparable<IntegerNode>, Math<IntegerNode> {
    private final int value;

    public IntegerNode(int value) {
        this.value = value;
    }

    public int getInteger() {
        return value;
    }

    @Override
    public int compareTo(IntegerNode other) {
        return getInteger() - other.getInteger();
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public IntegerNode add(IntegerNode other) {
        return new IntegerNode(getInteger() + other.getInteger());
    }

    @Override
    public IntegerNode sub(IntegerNode other) {
        return new IntegerNode(getInteger() - other.getInteger());
    }

    @Override
    public IntegerNode div(IntegerNode other) {
        return new IntegerNode(getInteger() / other.getInteger());
    }

    @Override
    public IntegerNode mul(IntegerNode other) {
        return new IntegerNode(getInteger() * other.getInteger());
    }

    @Override
    public Node getValue(Environment environment) {
        return this;
    }
}
