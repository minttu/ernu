package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;

import java.util.List;

/**
 * Match nodes hold case nodes, from which max one is evaluated.
 */
public class MatchNode implements Node {
    private final List<CaseNode> cases;

    public MatchNode(List<CaseNode> cases) {
        this.cases = cases;
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        for (CaseNode caseNode : cases) {
            if (caseNode.matches(environment)) {
                return caseNode.getValue(environment);
            }
        }
        return new NullNode();
    }

    @Override
    public String toString() {
        return "(match " + cases + ")";
    }
}
