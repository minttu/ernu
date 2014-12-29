package fi.imberg.juhani.ernu.interpreter.interfaces;

/**
 * Interface for all nodes in ernu that know math with each other.
 *
 * @param <T>
 */
public interface Mathable<T> {
    public Node add(T other);

    public Node sub(T other);

    public Node div(T other);

    public Node mul(T other);

    public Node mod(T other);
}
