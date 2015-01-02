package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.NullNode;
import fi.imberg.juhani.ernu.interpreter.nodes.StringNode;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

public class TypeFunctionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    Environment environment;
    TypeFunction typeFunction;

    @Before
    public void init() {
        environment = new Environment(false, null, "test");
        typeFunction = new TypeFunction();
    }

    @After
    public void destroy() {
        environment = null;
        typeFunction = null;
    }

    @Test
    public void mustBeOneArgument() throws RuntimeException {
        thrown.expect(fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException.class);
        thrown.expectMessage("type takes one argument only");

        typeFunction.call(environment, new ArrayList<>());
    }

    @Test
    public void works() throws RuntimeException {
        List<Node> args = new ArrayList<>();
        args.add(new NullNode());
        Node node = typeFunction.call(environment, args);
        Assert.assertTrue(node instanceof StringNode);
        Assert.assertEquals("NullNode", ((StringNode) node).getStringLiteral());
    }
}