package fi.imberg.juhani.ernu.repl.swing;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

/**
 * Log component of the SwingREPL
 */
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

    /**
     * Zooms the log in
     */
    public void zoomIn() {
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, getFont().getSize() + 6));
    }

    /**
     * Zooms out of the log
     */
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

    /**
     * Displays a comment
     * @param string
     */
    public void comment(String string) {
        append("# " + string + "\n");
    }

    /**
     * Displays a normal line
     * @param string
     */
    public void display(String string) {
        append(string + "\n");
    }

    /**
     * Displays an error
     * @param string
     */
    public void error(String string) {
        append("! " + string + "\n");
    }

    /**
     * Displays a string that was recieved as input from InputArea
     * @param str
     */
    public void input(String str) {
        String[] strs = str.split("\n");
        for (String st : strs) {
            if (st.length() > 0) {
                append("> " + st + "\n");
            }
        }
    }

    /**
     * Clears the log
     */
    public void clear() {
        setText("");
    }
}
