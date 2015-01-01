package fi.imberg.juhani.ernu.interpreter.interfaces;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

/**
 * Interface for all nodes that can be assigned to, or just work as identifiers.
 */
public interface Identifier {
    /**
     * As there is a getValue, there is also setValue, for binding the value to an identifier.
     *
     * @param environment Environment where this binding should happen
     * @param value       What to bind to this
     * @throws RuntimeException If anything goes haywire, this gets thrown
     */
    public void setValue(Environment environment, Node value) throws RuntimeException;
}
