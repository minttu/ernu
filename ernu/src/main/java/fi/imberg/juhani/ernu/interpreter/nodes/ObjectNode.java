package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.exceptions.UnknownAttributeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.interfaces.Object;

import java.util.Map;

/**
 * Represents an instance of a class.
 */
public class ObjectNode implements Node, Object {
    private final Map<String, Node> attributes;
    private final String doc;

    public ObjectNode(String doc, Map<String, Node> values) {
        this.doc = doc;
        this.attributes = values;
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        return this;
    }

    @Override
    public void setAttribute(String key, Node value) throws RuntimeException {
        attributes.put(key, value);
    }

    @Override
    public Node getAttribute(String key) throws RuntimeException {
        switch (key) {
            case "__doc__":
                return new StringNode(doc);
        }
        Node node = attributes.get(key);
        if (node == null) {
            throw new UnknownAttributeException(key);
        }
        return node;
    }

    @Override
    public boolean hasAttribute(String key) throws RuntimeException {
        try {
            getAttribute(key);
        } catch (UnknownAttributeException ignored) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "(object " + attributes.keySet() + ")";
    }
}
