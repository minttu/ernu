package fi.imberg.juhani.ernu.cli.command;

import fi.imberg.juhani.ernu.ErnuException;
import fi.imberg.juhani.ernu.interpreter.REPL;
import fi.imberg.juhani.ernu.util.Range;

/**
 * This command starts a REPL.
 */
public class REPLCommand implements Command {
    @Override
    public void call(String[] args) throws ErnuException {
        REPL repl = new REPL();
        repl.loop();
    }

    @Override
    public Range getRange() {
        return new Range(0, 0);
    }

    @Override
    public String getCommand() {
        return "repl";
    }

    @Override
    public String getDescription() {
        return "Start a REPL.";
    }
}
