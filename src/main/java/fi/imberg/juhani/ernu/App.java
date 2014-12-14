package fi.imberg.juhani.ernu;

import fi.imberg.juhani.ernu.lang.Parser;
import fi.imberg.juhani.ernu.lang.Token;
import fi.imberg.juhani.ernu.lang.TokenType;
import fi.imberg.juhani.ernu.lang.Tokenizer;
import fi.imberg.juhani.ernu.lang.exceptions.LangException;
import fi.imberg.juhani.ernu.lang.node.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class App {

    public static void printHelp() {
        System.out.println("Usage: ernu [COMMAND] [FILES]");
        System.out.println("commands:");
        System.out.println("  help        print this help");
        System.out.println("  parse       parse a file");
    }

    public static void main(String[] args) {

        if(args.length == 0) {
            printHelp();
            return;
        }

        switch (args[0]) {
            case "help":
                printHelp();
                break;
            case "parse":
                if(args.length < 2) {
                    System.err.println("No file given!");
                    System.exit(1);
                }
                Tokenizer tokenizer = new Tokenizer();
                Parser parser = new Parser(tokenizer);
                for(int i = 1; i < args.length; i++) {
                    byte[] bytes = new byte[0];
                    try {
                        bytes = Files.readAllBytes(Paths.get(args[i]));
                    } catch (IOException ex) {
                        System.err.println("No such file as \"" + args[i] + "\"!");
                        System.exit(1);
                    }
                    tokenizer.tokenize(new String(bytes, StandardCharsets.UTF_8) + "\n\n");
                    /*System.out.println(String.format("%79s", "").replace(" ", "-"));
                    System.out.println("File: " + args[i]);
                    System.out.println(String.format("%79s", "").replace(" ", "-"));

                    while (!tokenizer.isEmpty()) {
                        Token token = tokenizer.nextToken();
                        if (token.isEmpty()) {
                            continue;
                        }
                        System.out.println(token);
                    }*/
                }
                try {
                    while(!tokenizer.isEmpty()) {
                        Node node = parser.parseNode();
                        if(node != null) {
                            System.out.println(node);
                        }
                    }
                } catch (LangException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.err.println("Unknown command.");
                System.exit(1);
        }
    }
}
