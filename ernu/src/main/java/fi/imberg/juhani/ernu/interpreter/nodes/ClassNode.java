package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Callable;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.interfaces.Object;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassNode implements Node, Callable, Object {
    private final Map<String, Node> attributes;
    private final BlockNode initial;
    private final String doc;
    private boolean hasInitialized;

    public ClassNode(StringNode doc, BlockNode initial) {
        this.doc = doc.getStringLiteral();
        this.attributes = new HashMap<>();
        this.initial = initial;
        this.hasInitialized = false;
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        if(!hasInitialized) {
            Environment local = environment.subEnvironment();
            initial.getValue(local);
            this.setAttributes(local.getSymbols());
            hasInitialized = true;
        }
        return this;
    }

    public Map<String, Node> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Node> attributes) {
        this.attributes.putAll(attributes);
    }

    @Override
    public void setAttribute(String key, Node value) {
        attributes.put(key, value);
    }

    @Override
    public Node getAttribute(String key) {
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

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        Environment local = environment.subEnvironment();
        initial.getValue(local);
        ObjectNode node = new ObjectNode(doc, local.getSymbols());
        node.setAttribute("this", node);
        return node;
    }

    @Override
    public String toString() {
        return "(object " + attributes.keySet() + ")";
    }
}
