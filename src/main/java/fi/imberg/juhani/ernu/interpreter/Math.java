package fi.imberg.juhani.ernu.interpreter;

import fi.imberg.juhani.ernu.interpreter.node.Node;

public interface Math<T> {
    public Node add(T other);

    public Node sub(T other);

    public Node div(T other);

    public Node mul(T other);
}
