package fi.imberg.juhani.ernu;

import fi.imberg.juhani.ernu.cli.CLI;

/**
 * Main entry point into the program.
 */
public class App {

    public static void main(String[] args) {
        (new CLI()).run(args);
    }
}
