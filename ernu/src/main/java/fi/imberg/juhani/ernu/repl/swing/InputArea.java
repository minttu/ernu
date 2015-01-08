package fi.imberg.juhani.ernu.repl.swing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

    public void zoomIn() {
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, getFont().getSize() + 6));
    }

    public void zoomOut() {
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, getFont().getSize() - 6));
    }

    public boolean isCaretAtEnd() {
        return getText().length() == getCaretPosition();
    }

    public boolean ready() {
        return ready;
    }

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

    private void historyAdd(String str) {
        str = str.trim();
        if (history.size() == 0 || !history.get(history.size() - 1).equals(str)) {
            history.add(str.trim());
            historyPos = -1;
        }
    }

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

    public void ok(boolean bool) {
        if (!bool) {
            setBackground(new Color(255, 220, 220));
        } else {
            setBackground(Color.WHITE);
        }
    }
}
