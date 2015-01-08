package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.builtin.BuiltinFunction;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.exceptions.UnknownAttributeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Callable;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.interfaces.Object;

import java.util.List;

/**
 * A node that holds an environment, specifically used with importing environments to another one.
 */
public class EnvironmentNode implements Node, Object {
    /**
     * The environment this environment node wraps
     */
    private final Environment environment;

    /**
     * @param environment The environment this environment node wraps
     */
    public EnvironmentNode(Environment environment) {
        this.environment = environment;
        this.environment.addSymbol("__proxy__", new BuiltinFunction("Returns a proxy call node.",
                (Environment env, List<Node> args) -> getProxyTo(((StringNode) args.get(0)).getStringLiteral())));
    }

    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        return this;
    }

    @Override
    public void setAttribute(String key, Node value) throws RuntimeException {
        environment.addSymbol(key, value);
    }

    @Override
    public Node getAttribute(String key) throws RuntimeException {
        Node node = environment.getSymbols().get(key);
        if (node == null) {
            throw new UnknownAttributeException(key);
        }
        if(node instanceof Callable) {
            return new ProxyNode(environment, node);
        }
        return node;
    }

    @Override
    public boolean hasAttribute(String key) throws RuntimeException {
        return environment.getSymbols().containsKey(key);
    }

    /**
     * @param key Which attribute to get a proxy to
     * @return A new proxy node to the given attribute
     * @throws RuntimeException If anything goes haywire, this gets thrown
     */
    public Node getProxyTo(String key) throws RuntimeException {
        return new ProxyNode(environment, getAttribute(key));
    }

    @Override
    public String toString() {
        return "(" + environment.getFileName() + " " + environment.getSymbols().keySet() + ")";
    }
}
