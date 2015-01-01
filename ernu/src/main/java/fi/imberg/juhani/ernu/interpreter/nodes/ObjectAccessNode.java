package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Identifier;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.interfaces.Object;

/**
 * Accesses an object and either brings results or assigns nodes to the object.
 */
public class ObjectAccessNode implements Node, Identifier {
    /**
     * The node that must resolve into an object to be accessed
     */
    private final Node object;
    /**
     * The thing thats accessed in the object
     */
    private final Node thing;

    /**
     * @param obj   The node that must resolve into an object to be accessed
     * @param thing The thing thats accessed in the object
     */
    public ObjectAccessNode(Node obj, Node thing) {
        this.object = obj;
        this.thing = thing;
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        Node val = object.getValue(environment);
        if (!(val instanceof Object)) {
            throw new RuntimeException("Node not an object!");
        }
        Object objectNode = (Object) val;
        return objectNode.getAttribute(thing.toString());
    }

    @Override
    public void setValue(Environment environment, Node value) throws RuntimeException {
        Node val = object.getValue(environment);
        if (!(val instanceof Object)) {
            throw new RuntimeException("Node not an object!");
        }
        Object objectNode = (Object) val;
        objectNode.setAttribute(thing.toString(), value.getValue(environment));
    }

    @Override
    public String toString() {
        return object.toString() + "." + thing;
    }

    public Node getObject() {
        return object;
    }
}
