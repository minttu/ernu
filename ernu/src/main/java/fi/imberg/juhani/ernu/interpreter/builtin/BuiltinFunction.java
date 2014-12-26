package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.BlockNode;
import fi.imberg.juhani.ernu.interpreter.nodes.FunctionNode;

import java.util.ArrayList;

public class BuiltinFunction extends FunctionNode {
    public BuiltinFunction(String doc) {
        super(new ArrayList<Node>(),
                doc,
                new BlockNode(new ArrayList<Node>()));
    }
}
