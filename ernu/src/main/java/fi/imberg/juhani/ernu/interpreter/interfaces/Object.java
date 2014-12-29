package fi.imberg.juhani.ernu.interpreter.interfaces;

import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

/**
 * Interface for all nodes in ernu that have attributes.
 */
public interface Object {
    public void setAttribute(String key, Node value) throws RuntimeException;

    public Node getAttribute(String key) throws RuntimeException;

    public boolean hasAttribute(String key) throws RuntimeException;
}
