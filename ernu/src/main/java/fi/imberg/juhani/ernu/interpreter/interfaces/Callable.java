package fi.imberg.juhani.ernu.interpreter.interfaces;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

import java.util.List;

/**
 * Interface for all nodes in ernu that can be called like functions.
 */
public interface Callable {
    /**
     * Calls the node like a function
     * @param environment Environment where this execution should happen
     * @param arguments Arguments to the function
     * @return The resulting node
     * @throws RuntimeException
     */
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException;
}
