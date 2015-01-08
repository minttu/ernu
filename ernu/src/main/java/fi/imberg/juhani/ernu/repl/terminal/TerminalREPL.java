package fi.imberg.juhani.ernu.repl.terminal;

import fi.imberg.juhani.ernu.repl.REPLUI;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class TerminalREPL implements REPLUI {
    private final Scanner scanner;
    private final PrintStream printStream;
    private boolean ok;

    public TerminalREPL() {
        this.ok = true;
        this.scanner = new Scanner(System.in);
        this.printStream = new PrintStream(new FileOutputStream(FileDescriptor.out));
    }

    @Override
    public void comment(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(   ) ");
        stringBuilder.append(string);
        System.err.println(stringBuilder);
    }

    @Override
    public void display(String string) {
        printStream.println(string);
    }

    @Override
    public void error(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\033[31m(!!!) ");
        stringBuilder.append(string);
        stringBuilder.append("\033[0m");
        printStream.println(stringBuilder);
    }

    @Override
    public void ok(boolean bool) {
        this.ok = bool;
    }

    @Override
    public void raw(int b) {
        printStream.append((char) b);
    }

    @Override
    public String read() {
        if (ok) {
            printStream.print("(^_^) ");
        } else {
            printStream.print("(>_<) ");
        }
        return scanner.nextLine();
    }

    @Override
    public boolean ready() {
        return true;
    }
}
