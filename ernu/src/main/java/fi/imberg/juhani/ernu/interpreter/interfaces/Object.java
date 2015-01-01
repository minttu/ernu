package fi.imberg.juhani.ernu.interpreter.interfaces;

import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

/**
 * Interface for all nodes in ernu that have attributes.
 */
public interface Object {
    /**
     * Sets an attribute, like a.b = c
     *
     * @param key   The name of the attribute
     * @param value The node that gets assigned
     * @throws RuntimeException If anything goes haywire, this gets thrown
     */
    public void setAttribute(String key, Node value) throws RuntimeException;

    /**
     * Gets an attribute which is indexed by the key
     *
     * @param key The name of the attribute
     * @return The node
     * @throws RuntimeException If anything goes haywire, this gets thrown
     */
    public Node getAttribute(String key) throws RuntimeException;

    /**
     * Does this object have the requested attribute
     *
     * @param key The name of the attribute
     * @return True if this object has the requested attribute
     * @throws RuntimeException If anything goes haywire, this gets thrown
     */
    public boolean hasAttribute(String key) throws RuntimeException;
}
