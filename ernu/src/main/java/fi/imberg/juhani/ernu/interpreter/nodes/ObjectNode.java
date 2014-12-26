package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.*;
import fi.imberg.juhani.ernu.interpreter.interfaces.Object;

import java.util.Map;

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
        switch(key) {
            case "__doc__":
                return new StringNode(doc);
        }
        Node node = attributes.get(key);
        if (node == null) {
            return new NullNode();
        }
        return node;
    }
}
