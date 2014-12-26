package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Identifier;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;

public class ObjectAccessNode implements Node, Identifier {
    private final Node obj;
    private final Node thing;

    public ObjectAccessNode(Node obj, Node thing) {
        this.obj = obj;
        this.thing = thing;
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        Node val = obj.getValue(environment);
        if (!(val instanceof ObjectNode)) {
            throw new RuntimeException("Node not an object!");
        }
        ObjectNode objectNode = (ObjectNode) val;
        return objectNode.getContent(thing.toString());
    }

    @Override
    public void setValue(Environment environment, Node value) throws RuntimeException {
        Node val = obj.getValue(environment);
        if (!(val instanceof ObjectNode)) {
            throw new RuntimeException("Node not an object!");
        }
        ObjectNode objectNode = (ObjectNode) val;
        objectNode.setContent(thing.toString(), value.getValue(environment));
    }

    @Override
    public String toString() {
        return obj.toString() + "." + thing;
    }
}
