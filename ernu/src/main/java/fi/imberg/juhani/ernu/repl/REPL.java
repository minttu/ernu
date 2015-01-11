package fi.imberg.juhani.ernu.repl;

import fi.imberg.juhani.ernu.ErnuException;
import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.NullNode;
import fi.imberg.juhani.ernu.parser.ErnuParser;
import fi.imberg.juhani.ernu.parser.Tokenizer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;
import java.util.Scanner;

/**
 * Read-Evaluate-Print loop. An interactive interface to Ernu.
 */
public class REPL extends Thread {
    private final Scanner scanner;
    private final Tokenizer tokenizer;
    private final ErnuParser parser;
    private final Environment environment;
    private final REPLUI ui;

    public REPL(REPLUI ui) {
        this.ui = ui;
        this.scanner = new Scanner(System.in);
        this.tokenizer = new Tokenizer();
        this.parser = new ErnuParser(tokenizer);
        this.environment = new Environment(true, null, "repl");
        greet();
        execute("import math");
        execute("import functional");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                ui.raw(b);
            }
        }));
    }

    /**
     * Executes a string directly in the REPL.
     *
     * @param string The sourcecode to execute
     */
    public void execute(String string) {
        ui.comment(string);
        try {
            print(eval(string + "\n\n"));
        } catch (ErnuException e) {
            ui.error(e.getMessage());
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
            ui.error(e.getMessage());
        }
        ui.comment("ernu lang");
        ui.comment("git rev: " + properties.getProperty("git.commit.id.abbrev"));
    }

    /**
     * Reads the next line from the UI.
     *
     * @return the string that was red
     */
    private String read() {
        return ui.read() + "\n\n";
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
            ui.display(value.toString());
        }
    }

    /**
     * The main loop. Prints the evaluated value of whatever has been typed.
     */
    @Override
    public void run() {
        while (true) {
            if (!ui.ready()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    return;
                }
                continue;
            }
            try {
                print(eval(read()));
                ui.ok(true);
            } catch (Exception e) {
                ui.error(e.getMessage());
                ui.ok(false);
                while (!tokenizer.isEmpty()) {
                    tokenizer.nextToken();
                }
            }
        }
    }
}
