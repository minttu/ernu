package fi.imberg.juhani.ernu.interpreter.interfaces;

/**
 * Interface for overriding toString for some reason when printing for the user.
 */
public interface Printable {
    /**
     *
     * @return A string representation of the node.
     */
    public String toPrintable();
}
