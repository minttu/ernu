package fi.imberg.juhani.ernu.repl.swing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Input component of the SwingREPL
 */
public class InputArea extends JTextArea {
    private boolean ready;
    private List<String> history;
    private int historyPos;

    public InputArea() {
        super(3, 80);
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        addKeyListener(new InputKeyListener(this));
        setBorder(BorderFactory.createLineBorder(new Color(66, 66, 66), 1));
        history = new ArrayList<>();
        historyPos = 0;
        ready = false;
    }

    /**
     * Zooms the input area in
     */
    public void zoomIn() {
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, getFont().getSize() + 6));
    }

    /**
     * Zooms the input area out
     */
    public void zoomOut() {
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, getFont().getSize() - 6));
    }

    /**
     * Is the caret at the end of the input
     * @return true if caret is at the end of the input
     */
    public boolean isCaretAtEnd() {
        return getText().length() == getCaretPosition();
    }

    /**
     * Do we have some input to give
     * @return true if there is some input
     */
    public boolean ready() {
        return ready;
    }

    /**
     * Goes to the previous input line in the history
     */
    public void historyPrev() {
        if (historyPos == -1) {
            historyPos = history.size() - 1;
            setText(history.get(history.size() - 1));
        } else {
            historyPos = Math.max(0, historyPos - 1);
        }
        if (history.size() > 0 && historyPos >= 0) {
            setText(history.get(historyPos));
        }
    }

    /**
     * Goes to the next input line in the history
     */
    public void historyNext() {
        if (history.size() == 0) {
            return;
        }
        if (historyPos == -1 || historyPos == history.size() - 1) {
            setText("");
            historyPos = -1;
            return;
        }
        historyPos = Math.min(history.size() - 1, historyPos + 1);
        setText(history.get(historyPos));

    }

    /**
     * Adds a string to the history
     * @param str The string to add
     */
    private void historyAdd(String str) {
        str = str.trim();
        if (history.size() == 0 || !history.get(history.size() - 1).equals(str)) {
            history.add(str.trim());
            historyPos = -1;
        }
    }

    /**
     * Reads the current input and resets the current input
     * @return the current input
     */
    public String read() {
        String text = getText();
        setText("");
        historyAdd(text);
        ready = false;
        return text;
    }

    public void setReady() {
        ready = true;
    }

    /**
     * Sets the background color according to if the last execute was successful
     * @param bool was the last execute successful
     */
    public void ok(boolean bool) {
        if (!bool) {
            setBackground(new Color(255, 220, 220));
        } else {
            setBackground(Color.WHITE);
        }
    }
}
