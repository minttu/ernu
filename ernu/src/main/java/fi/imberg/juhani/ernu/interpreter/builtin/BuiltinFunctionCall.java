package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;

import java.util.List;

public interface BuiltinFunctionCall {
    public Node call(Environment environment, List<Node> args) throws RuntimeException;
}
