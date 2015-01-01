package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;

/**
 * A for node represents a for loop.
 */
public class ForNode implements Node {
    /**
     * The node that gets assigned the generators current value each loop
     */
    private final Node identifier;
    /**
     * Something that resolves into a list for iteration
     */
    private final Node generator;
    /**
     * The body that gets executed each loop
     */
    private final BlockNode body;

    /**
     * @param identifier The node that gets assigned the generators current value each loop
     * @param generator  Something that resolves into a list for iteration
     * @param body       The body that gets executed each loop
     */
    public ForNode(Node identifier, Node generator, BlockNode body) {
        this.identifier = identifier;
        this.generator = generator;
        this.body = body;
    }

    @Override
    public String toString() {
        return "(for " + identifier + " -> " + generator + " " + body + ")";
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        Node generated = generator.getValue(environment);
        Environment local = environment;
        if (!(generated instanceof ArrayNode)) {
            throw new RuntimeException("Generated not a array");
        }
        if (!(identifier instanceof IdentifierNode)) {
            throw new RuntimeException("Not identifier");
        }
        ArrayNode generatedArray = (ArrayNode) generated;
        Node value = new NullNode();
        for (int i = 0; i < generatedArray.length(); i++) {
            local.addSymbol(identifier.toString(), generatedArray.get(i));
            value = body.getValue(local);
        }
        return value;
    }
}
