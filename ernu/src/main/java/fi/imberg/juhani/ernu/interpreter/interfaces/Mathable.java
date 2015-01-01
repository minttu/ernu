package fi.imberg.juhani.ernu.interpreter.interfaces;

/**
 * Interface for all nodes in ernu that know math with each other.
 *
 * @param <T> The type that this can do math with
 */
public interface Mathable<T> {
    /**
     * Adds this node to other
     *
     * @param other The other node in the math
     * @return A new node which has the value
     */
    public Node add(T other);

    /**
     * Subtracts the other node from this
     *
     * @param other The other node in the math
     * @return A new node which has the value
     */
    public Node sub(T other);

    /**
     * Divides this node with the other
     *
     * @param other The other node in the math
     * @return A new node which has the value
     */
    public Node div(T other);

    /**
     * Multiplies this node with the other
     *
     * @param other The other node in the math
     * @return A new node which has the value
     */
    public Node mul(T other);

    /**
     * This modulo other
     *
     * @param other The other node in the math
     * @return A new node which has the value
     */
    public Node mod(T other);
}
