package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.node.BlockNode;
import fi.imberg.juhani.ernu.interpreter.node.FunctionNode;
import fi.imberg.juhani.ernu.interpreter.node.Node;

import java.util.ArrayList;

public class BuiltinFunction extends FunctionNode {
    public BuiltinFunction(String doc) {
        super(new ArrayList<Node>(),
                doc,
                new BlockNode(new ArrayList<Node>()));
    }
}
