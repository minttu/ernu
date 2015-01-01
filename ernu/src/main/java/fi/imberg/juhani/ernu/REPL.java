package fi.imberg.juhani.ernu;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.NullNode;
import fi.imberg.juhani.ernu.parser.ErnuParser;
import fi.imberg.juhani.ernu.parser.Tokenizer;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

/**
 * Read-Evaluate-Print loop. An interactive interface to Ernu.
 */
public class REPL {
    private final Scanner scanner;
    private final Tokenizer tokenizer;
    private final ErnuParser parser;
    private final Environment environment;
    private boolean lastOk;

    public REPL() {
        this.scanner = new Scanner(System.in);
        this.tokenizer = new Tokenizer();
        this.parser = new ErnuParser(tokenizer);
        this.environment = new Environment(true, null, "repl");
        this.lastOk = true;
        greet();
        execute("import math");
    }

    /**
     * Executes a string directly in the REPL.
     *
     * @param string The sourcecode to execute
     */
    public void execute(String string) {
        System.out.println("(^_^) " + string);
        try {
            print(eval(string + "\n\n"));
        } catch (ErnuException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints a greeting.
     */
    private void greet() {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("/git.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("(   ) ernu lang");
        System.out.println("(   ) git rev: " + properties.getProperty("git.commit.id.abbrev"));
    }

    /**
     * Reads the next line from stdin.
     *
     * @return the string that was red
     */
    private String read() {
        if (lastOk) {
            System.out.print("(^_^) ");
        } else {
            System.out.print("(>_<) ");
        }
        return scanner.nextLine() + "\n\n";
    }

    /**
     * Evaluates a string.
     *
     * @param source The string to be evaluated.
     * @return The resulting node.
     * @throws ErnuException
     */
    private Node eval(String source) throws ErnuException {
        tokenizer.tokenize(source);
        Node node = new NullNode();
        while (!tokenizer.isEmpty()) {
            Node current = parser.parseNode();
            if (current != null) {
                node.getValue(environment);
                node = current;
            }
        }
        return node;
    }

    /**
     * Retrieves the nodes value and prints it.
     *
     * @param node The node to have its value printed.
     * @throws RuntimeException
     */
    private void print(Node node) throws RuntimeException {
        Node value = node.getValue(this.environment);
        if (!(value instanceof NullNode)) {
            System.out.println(value);
        }
    }

    /**
     * The main loop. Prints the evaluated value of whatever has been typed.
     */
    public void loop() {
        while (true) {
            try {
                print(eval(read()));
                lastOk = true;
            } catch (ErnuException e) {
                System.err.println("\033[31m" + e.getMessage() + "\033[0m");
                lastOk = false;
                while (!tokenizer.isEmpty()) {
                    tokenizer.nextToken();
                }
            }
        }
    }
}
