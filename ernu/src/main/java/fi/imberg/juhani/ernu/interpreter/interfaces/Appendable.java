package fi.imberg.juhani.ernu.interpreter.interfaces;

/**
 * Interface for all nodes in ernu that can be appended or prepended to.
 */
public interface Appendable {
    /**
     * Appends the given node to a copy of this
     * @param node What to append to a copy of this
     * @return A copy of this with the node appended
     */
    public Node append(Node node);

    /**
     * Prepends the given node to a copy of this
     * @param node What to prepend to a copy of this
     * @return A copy of this with the node prepended
     */
    public Node prepend(Node node);
}
