package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.builtin.BuiltinFunction;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.exceptions.UnknownAttributeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.interfaces.Object;

import java.util.List;

public class EnvironmentNode implements Node, Object {
    private final Environment environment;

    public EnvironmentNode(Environment environment) {
        this.environment = environment;
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
        switch (key) {
            case "__proxy__":
                return new BuiltinFunction("Returns a proxy call node.",
                        (Environment env, List<Node> args) -> getProxyTo(((StringNode) args.get(0)).getStringLiteral()));
        }
        return environment.getSymbol(key);
    }

    @Override
    public boolean hasAttribute(String key) throws RuntimeException {
        try {
            environment.getSymbol(key);
            return true;
        } catch (UnknownAttributeException ignored) {
            return false;
        }
    }

    public Node getProxyTo(String key) throws RuntimeException {
        return new ProxyNode(environment, getAttribute(key));
    }

    @Override
    public String toString() {
        return "(" + environment.getFileName() + " " + environment.getSymbols().keySet() + ")";
    }
}
