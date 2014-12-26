package fi.imberg.juhani.ernu.interpreter.interfaces;

import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

public interface Object {
    public void setAttribute(String key, Node value) throws RuntimeException;

    public Node getAttribute(String key) throws RuntimeException;
}
