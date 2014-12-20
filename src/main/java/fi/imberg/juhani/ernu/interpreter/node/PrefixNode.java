package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.parser.TokenType;

public class PrefixNode implements Node {
    private final TokenType type;
    private final Node node;

    public PrefixNode(TokenType type, Node node) {
        this.type = type;
        this.node = node;
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        Node node = this.node.getValue(environment);
        switch (type) {
            case NOT:
                if(!(node instanceof BooleanNode)) {
                    throw new RuntimeException("Must be boolean to be notted!");
                }
                return new BooleanNode(!((BooleanNode) node).isTrue());
        }
        return new NullNode();
    }
}
