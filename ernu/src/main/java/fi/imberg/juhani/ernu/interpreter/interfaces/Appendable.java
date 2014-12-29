package fi.imberg.juhani.ernu.interpreter.interfaces;

/**
 * Interface for all nodes in ernu that can be appended or prepended to.
 */
public interface Appendable {
    public Node append(Node node);

    public Node prepend(Node node);
}
