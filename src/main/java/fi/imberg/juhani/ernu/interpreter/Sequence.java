package fi.imberg.juhani.ernu.interpreter;

import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.node.Node;

public interface Sequence {
    public int length();

    public Node get(int index) throws RuntimeException;

    public void set(int index, Node value) throws RuntimeException;
}
