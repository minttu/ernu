package fi.imberg.juhani.ernu.cli.command;

import fi.imberg.juhani.ernu.ErnuException;
import fi.imberg.juhani.ernu.util.Range;

public interface Command {
    public void call(String[] args) throws ErnuException;
    public Range getRange();
    public String getCommand();
    public String getDescription();
}
