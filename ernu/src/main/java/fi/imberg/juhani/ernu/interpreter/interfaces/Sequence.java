package fi.imberg.juhani.ernu.interpreter.interfaces;

import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

/**
 * Interface for all list type objects in ernu.
 */
public interface Sequence {
    /**
     * @return How long the sequence is
     */
    public int length();

    /**
     * Gets an indexed value.
     *
     * @param index The index to retrieve
     * @return Node which sits at the index
     * @throws RuntimeException If anything goes haywire, this gets thrown
     */
    public Node get(int index) throws RuntimeException;

    /**
     * @param index The index to set to
     * @param value The value to set the index to
     * @throws RuntimeException If anything goes haywire, this gets thrown
     */
    public void set(int index, Node value) throws RuntimeException;

    /**
     * @return A new empty sequence of the same kind
     */
    public Node newEmpty();
}
