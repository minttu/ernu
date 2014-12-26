package fi.imberg.juhani.ernu.interpreter.interfaces;

import fi.imberg.juhani.ernu.interpreter.node.Node;

public interface Object {
    public void setContent(String key, Node value);

    public Node getContent(String key);
}
