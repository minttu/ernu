package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Callable;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;

import java.util.List;

/**
 * Proxy nodes proxy calls of functions into other namespaces.
 */
public class ProxyNode implements Node, Callable {

    /**
     * In which environment the identifier is defined
     */
    private final Environment env;
    /**
     * The identifier this proxies to
     */
    private final Node identifier;

    /**
     * @param environment In which environment the identifier is defined
     * @param identifier  The identifier this proxies to
     */
    public ProxyNode(Environment environment, Node identifier) {
        this.env = environment;
        this.identifier = identifier;
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        Node node = getValue(env);
        if (!(node instanceof Callable)) {
            throw new RuntimeException("Node must be callable!");
        }
        return ((Callable) node).call(env, arguments);
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        return identifier.getValue(env);
    }

    @Override
    public String toString() {
        return "(proxy " + env + "." + identifier + ")";
    }
}
