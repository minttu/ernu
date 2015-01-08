package fi.imberg.juhani.ernu.repl.swing;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class LogArea extends JTextArea {
    public LogArea() {
        super(10, 80);
        setEditable(false);
        setLineWrap(true);
        setWrapStyleWord(true);
        setPreferredSize(new Dimension(360, 360));
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        setBackground(new Color(222, 222, 222));
        setBorder(BorderFactory.createLineBorder(new Color(66, 66, 66), 1));
        ((DefaultCaret) getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }

    public void zoomIn() {
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, getFont().getSize() + 6));
    }

    public void zoomOut() {
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, getFont().getSize() - 6));
    }

    @Override
    public void append(String string) {
        int news = 0;
        for (char ch : string.toCharArray()) {
            if (ch == '\n') {
                news++;
            }
        }
        setRows(getRows() + news);
        super.append(string);
    }

    public void comment(String string) {
        append("# " + string + "\n");
    }

    public void display(String string) {
        append(string + "\n");
    }

    public void error(String string) {
        append("! " + string + "\n");
    }

    public void input(String str) {
        String[] strs = str.split("\n");
        for (String st : strs) {
            if (st.length() > 0) {
                append("> " + st + "\n");
            }
        }
    }

    public void clear() {
        setText("");
    }
}
