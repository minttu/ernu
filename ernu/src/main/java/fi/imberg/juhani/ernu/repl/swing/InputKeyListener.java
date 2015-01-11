package fi.imberg.juhani.ernu.repl.swing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Listens to the InputAreas keys
 */
public class InputKeyListener implements KeyListener {
    private final InputArea inputArea;

    /**
     *
     * @param inputArea The InputArea to listen to
     */
    public InputKeyListener(InputArea inputArea) {
        this.inputArea = inputArea;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.isAltDown()) {
                inputArea.append("\n");
                return;
            }
            if (inputArea.isCaretAtEnd() || e.isControlDown()) {
                inputArea.setReady();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP && e.isControlDown()) {
            inputArea.historyPrev();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && e.isControlDown()) {
            inputArea.historyNext();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
