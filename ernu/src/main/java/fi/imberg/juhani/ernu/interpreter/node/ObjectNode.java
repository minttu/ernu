package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.*;
import fi.imberg.juhani.ernu.interpreter.interfaces.Object;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectNode implements Node, Callable, Object {
    private final Map<String, Node> contents;
    private final BlockNode initial;
    public ObjectNode(BlockNode initial) {
        this.contents = new HashMap<>();
        this.initial = initial;
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        return this;
    }

    public Map<String, Node> getContents() {
        return contents;
    }

    @Override
    public void setContent(String key, Node value) {
        contents.put(key, value);
    }

    public void setContents(Map<String, Node> contents) {
        this.contents.putAll(contents);
    }

    @Override
    public Node getContent(String key) {
        Node node = contents.get(key);
        if(node == null) {
            return new NullNode();
        }
        return node;
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        ObjectNode objectNode = new ObjectNode(new BlockNode());
        Environment local = environment.subEnvironment();
        initial.getValue(local);
        objectNode.setContents(local.getSymbols());
        return objectNode;
    }

    @Override
    public String toString() {
        return "(object " + contents.keySet() + ")";
    }
}
