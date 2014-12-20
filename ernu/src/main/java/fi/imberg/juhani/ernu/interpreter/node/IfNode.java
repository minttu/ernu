package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

public class IfNode implements Node {
    private final Node conditional;
    private final BlockNode trueBranch;
    private final BlockNode falseBranch;

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
