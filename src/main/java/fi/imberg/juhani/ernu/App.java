package fi.imberg.juhani.ernu;

import fi.imberg.juhani.ernu.cli.CLI;

public class App {

    public static void main(String[] args) {
        CLI cli = new CLI();
        cli.parseArguments(args);
    }
}
