package fi.imberg.juhani.ernu.interpreter.interfaces;

import fi.imberg.juhani.ernu.interpreter.node.Node;

public interface Mathable<T> {
    public Node add(T other);

    public Node sub(T other);

    public Node div(T other);

    public Node mul(T other);

    public Node mod(T other);
}
