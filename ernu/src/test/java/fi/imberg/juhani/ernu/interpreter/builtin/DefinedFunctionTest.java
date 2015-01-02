package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.BooleanNode;
import fi.imberg.juhani.ernu.interpreter.nodes.NullNode;
import fi.imberg.juhani.ernu.interpreter.nodes.StringNode;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

public class DefinedFunctionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    Environment environment;
    DefinedFunction definedFunction;

    @Before
    public void init() {
        environment = new Environment(false, null, "test");
        definedFunction = new DefinedFunction();
    }

    @After
    public void destroy() {
        environment = null;
        definedFunction = null;
    }

    @Test
    public void mustGetOneArgument() throws RuntimeException {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("defined takes one argument only");

        definedFunction.call(environment, new ArrayList<>());
    }

    @Test
    public void argumentMustBeAAstring() throws RuntimeException {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("defineds argument must be a string node");

        List<Node> nodeList = new ArrayList<>();
        nodeList.add(new NullNode());
        definedFunction.call(environment, nodeList);
    }

    @Test
    public void works() throws RuntimeException {
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(new StringNode("defined"));
        Node node = definedFunction.call(environment, nodeList);
        Assert.assertTrue(node instanceof BooleanNode);
        Assert.assertTrue(((BooleanNode) node).isTrue());

        nodeList = new ArrayList<>();
        nodeList.add(new StringNode("sdfsdf"));
        node = definedFunction.call(environment, nodeList);
        Assert.assertTrue(node instanceof BooleanNode);
        Assert.assertFalse(((BooleanNode) node).isTrue());
    }
}