package fi.imberg.juhani.ernu.repl.swing;

import fi.imberg.juhani.ernu.repl.REPLUI;

import javax.swing.*;
import java.awt.*;

/**
 * A REPL UI with Swing
 */
public class SwingREPL extends JFrame implements REPLUI {
    private LogArea log;
    private InputArea input;

    public SwingREPL() {
        super("ernu Graphical Read-Evaluate-Print Loop");

        JMenuBar menuBar = new JMenuBar();
        JMenu ernuMenu = new JMenu("Ernu");
        JMenu displayMenu = new JMenu("Display");

        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(e -> System.exit(0));
        ernuMenu.add(quit);

        JMenuItem zoomIn = new JMenuItem("Zoom in");
        zoomIn.addActionListener(e -> {
            log.zoomIn();
            input.zoomIn();
        });
        displayMenu.add(zoomIn);

        JMenuItem zoomOut = new JMenuItem("Zoom out");
        zoomOut.addActionListener(e -> {
            log.zoomOut();
            input.zoomOut();
        });
        displayMenu.add(zoomOut);

        JMenuItem clear = new JMenuItem("Clear");
        clear.addActionListener(e -> log.clear());
        displayMenu.add(clear);

        menuBar.add(ernuMenu);
        menuBar.add(displayMenu);
        getContentPane().add(menuBar, BorderLayout.PAGE_START);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        log = new LogArea();
        JScrollPane scrollPane = new JScrollPane(log);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        panel.add(scrollPane, c);

        input = new InputArea();

        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0;
        panel.add(input, c);

        getContentPane().add(panel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    @Override
    public void comment(String string) {
        log.comment(string);
    }

    @Override
    public void display(String string) {
        log.display(string);
    }

    @Override
    public void error(String string) {
        log.error(string);
    }

    @Override
    public void ok(boolean bool) {
        input.ok(bool);
    }

    @Override
    public void raw(int b) {
        log.append(new String(new char[]{(char) b}));
    }

    @Override
    public String read() {
        String str = input.read();
        log.input(str);
        return str;
    }

    @Override
    public boolean ready() {
        return input.ready();
    }
}
