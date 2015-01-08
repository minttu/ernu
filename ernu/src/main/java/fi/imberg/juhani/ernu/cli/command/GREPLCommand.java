package fi.imberg.juhani.ernu.cli.command;

import fi.imberg.juhani.ernu.ErnuException;
import fi.imberg.juhani.ernu.repl.REPL;
import fi.imberg.juhani.ernu.repl.swing.SwingREPL;
import fi.imberg.juhani.ernu.util.Range;

public class GREPLCommand implements Command {
    @Override
    public void call(String[] args) throws ErnuException {
        javax.swing.SwingUtilities.invokeLater(() -> new REPL(new SwingREPL()).start());
    }

    @Override
    public Range getRange() {
        return new Range(0, 0);
    }

    @Override
    public String getCommand() {
        return "grepl";
    }

    @Override
    public String getDescription() {
        return "Start a graphical REPL.";
    }
}
