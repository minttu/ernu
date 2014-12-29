package fi.imberg.juhani.ernu.interpreter.interfaces;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

/**
 * Interface for all nodes that can be assigned to, or just work as identifiers.
 */
public interface Identifier {
    public void setValue(Environment environment, Node value) throws RuntimeException;
}
