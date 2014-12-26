package fi.imberg.juhani.ernu.interpreter;

import fi.imberg.juhani.ernu.ErnuException;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.NullNode;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Tokenizer;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class REPL {
    private final Scanner scanner;
    private final Tokenizer tokenizer;
    private final Parser parser;
    private final Environment environment;
    private boolean lastOk;

    public REPL() {
        this.scanner = new Scanner(System.in);
        this.tokenizer = new Tokenizer();
        this.parser = new Parser(tokenizer);
        this.environment = new Environment("repl");
        this.lastOk = true;
        greet();
        execute("import(\"math\", \"functional\")");
    }

    public void execute(String string) {
        System.out.println("(^_^) " + string);
        try {
            print(eval(string + "\n\n"));
        } catch (ErnuException e) {
            e.printStackTrace();
        }
    }

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

    private String read() {
        if (lastOk) {
            System.out.print("(^_^) ");
        } else {
            System.out.print("(>_<) ");
        }
        return scanner.nextLine() + "\n\n";
    }

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

    private void print(Node node) throws RuntimeException {
        Node value = node.getValue(this.environment);
        if (!(value instanceof NullNode)) {
            System.out.println(value);
        }
    }

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
