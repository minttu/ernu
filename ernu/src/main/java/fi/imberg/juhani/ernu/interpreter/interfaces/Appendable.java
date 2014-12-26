package fi.imberg.juhani.ernu.interpreter.interfaces;

import fi.imberg.juhani.ernu.interpreter.node.Node;

public interface Appendable {
    public Node append(Node node);

    public Node prepend(Node node);
}
