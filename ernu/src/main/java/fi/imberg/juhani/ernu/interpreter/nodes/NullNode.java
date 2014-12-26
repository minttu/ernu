package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;

public class NullNode implements Node {
    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof NullNode);
    }

    @Override
    public String toString() {
        return "null";
    }
}
