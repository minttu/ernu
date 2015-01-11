package fi.imberg.juhani.ernu.repl;

/**
 * Abstracts the UI portion of the REPL.
 */
public interface REPLUI {
    /**
     * Display a comment
     * @param string The string to display
     */
    public void comment(String string);

    /**
     * Display a normal string
     * @param string The string to display
     */
    public void display(String string);

    /**
     * Display an error
     * @param string The string to display
     */
    public void error(String string);

    /**
     * Informs the UI if the last execute was ok or not
     * @param bool Was last execute ok or not
     */
    public void ok(boolean bool);

    /**
     * Displays a raw byte.
     * @param b The byte to display
     */
    public void raw(int b);

    /**
     * Returns the next command from the UI
     * @return The command that was received from the user
     */
    public String read();

    /**
     * Does the UI have a line to read
     * @return Is the UI ready to give more input
     */
    public boolean ready();
}
