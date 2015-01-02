package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.NullNode;
import fi.imberg.juhani.ernu.interpreter.nodes.NumberNode;
import fi.imberg.juhani.ernu.interpreter.nodes.StringNode;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

public class LenFunctionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    Environment environment;
    LenFunction lenFunction;

    @Before
    public void init() {
        environment = new Environment(false, null, "test");
        lenFunction = new LenFunction();
    }

    @After
    public void destroy() {
        environment = null;
        lenFunction = null;
    }

    @Test
    public void takesExactlyOneArgument() throws RuntimeException {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("len takes exactly one argument");

        lenFunction.call(environment, new ArrayList<>());
    }

    @Test
    public void requiresAObject() throws RuntimeException {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("lens argument should be a sequence");

        List<Node> nodeList = new ArrayList<>();
        nodeList.add(new NullNode());

        lenFunction.call(environment, nodeList);
    }

    @Test
    public void works() throws RuntimeException {
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(new StringNode("123"));
        Node node = lenFunction.call(environment, nodeList);
        Assert.assertTrue(node instanceof NumberNode);
        Assert.assertEquals(3, ((NumberNode) node).getInteger());
    }
}