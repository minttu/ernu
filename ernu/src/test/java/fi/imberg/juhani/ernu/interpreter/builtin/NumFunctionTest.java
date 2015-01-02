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

public class NumFunctionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    Environment environment;
    NumFunction numFunction;

    @Before
    public void init() {
        environment = new Environment(false, null, "test");
        numFunction = new NumFunction();
    }

    @After
    public void destroy() {
        environment = null;
        numFunction = null;
    }

    @Test
    public void needsOneArgument() throws RuntimeException {
        thrown.expect(fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException.class);
        thrown.expectMessage("num takes one argument only");

        numFunction.call(environment, new ArrayList<>());
    }

    @Test
    public void argumentNeedsToBeString() throws RuntimeException {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("num takes a string only");

        List<Node> nodeList = new ArrayList<>();
        nodeList.add(new NullNode());

        numFunction.call(environment, nodeList);
    }

    @Test
    public void works() throws RuntimeException {
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(new StringNode("3"));

        Node node = numFunction.call(environment, nodeList);
        Assert.assertTrue(node instanceof NumberNode);
        Assert.assertEquals(3, ((NumberNode) node).getInteger());
    }
}