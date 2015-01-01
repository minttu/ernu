package fi.imberg.juhani.ernu.interpreter.interfaces;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

/**
 * Interface for all expressions in ernu.
 */
public interface Node {
    /**
     * Retuns the value of this node in the context of the given environment
     * @param environment The context of this request
     * @return Whatever this evaluates to
     * @throws RuntimeException
     */
    public Node getValue(Environment environment) throws RuntimeException;
}
