package fi.imberg.juhani.ernu.interpreter.interfaces;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

/**
 * Interface for all expressions in ernu.
 */
public interface Node {
    public Node getValue(Environment environment) throws RuntimeException;
}
