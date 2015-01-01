package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;

/**
 * If nodes are just your everyday if/else/end structure. To be replaced.
 */
public class IfNode implements Node {
    /**
     * The first branches conditional node, must resolve into a BooleanNode
     */
    private final Node conditional;
    /**
     * The trueBranch gets executed if the conditional is true
     */
    private final BlockNode trueBranch;
    /**
     * The falseBranch gets executed if the conditional is false
     */
    private final BlockNode falseBranch;

    /**
     * @param conditional The first branches conditional node, must resolve into a BooleanNode
     * @param trueBranch  The trueBranch gets executed if the conditional is true
     * @param falseBranch The falseBranch gets executed if the conditional is false
     */
    public IfNode(Node conditional, BlockNode trueBranch, BlockNode falseBranch) {
        this.conditional = conditional;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    @Override
    public String toString() {
        return "(if " + conditional + " " + trueBranch + " " + falseBranch + ")";
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        Node truth = conditional.getValue(environment);
        if (!(truth instanceof BooleanNode)) {
            throw new RuntimeException("Conditional is not a boolean");
        }

        if (((BooleanNode) truth).isTrue()) {
            return trueBranch.getValue(environment);
        } else {
            return falseBranch.getValue(environment);
        }
    }
}
