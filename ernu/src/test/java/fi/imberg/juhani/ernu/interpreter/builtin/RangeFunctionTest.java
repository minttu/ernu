package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.ArrayNode;
import fi.imberg.juhani.ernu.interpreter.nodes.NullNode;
import fi.imberg.juhani.ernu.interpreter.nodes.NumberNode;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

public class RangeFunctionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    Environment environment;
    RangeFunction rangeFunction;

    @Before
    public void init() {
        environment = new Environment(false, null, "test");
        rangeFunction = new RangeFunction();
    }

    @After
    public void destroy() {
        environment = null;
        rangeFunction = null;
    }

    @Test
    public void mustBeOneArgument() throws RuntimeException {
        thrown.expect(fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException.class);
        thrown.expectMessage("range takes at least one argument");

        rangeFunction.call(environment, new ArrayList<>());
    }

    @Test
    public void cantBeMoreThan3Arguments() throws RuntimeException {
        thrown.expect(fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException.class);
        thrown.expectMessage("range takes three arguments at most");

        List<Node> nodeList = new ArrayList<>();
        nodeList.add(new NullNode());
        nodeList.add(new NullNode());
        nodeList.add(new NullNode());
        nodeList.add(new NullNode());

        rangeFunction.call(environment, nodeList);
    }

    @Test
    public void argumentsNeedToBeNumbers() throws RuntimeException {
        thrown.expect(fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException.class);
        thrown.expectMessage("range takes number nodes as arguments");

        List<Node> nodeList = new ArrayList<>();
        nodeList.add(new NullNode());

        rangeFunction.call(environment, nodeList);
    }

    public void doRangeTest(int[] argsArray, int[] expected) throws RuntimeException {
        List<Node> args = new ArrayList<>();
        for (int arg : argsArray) {
            args.add(new NumberNode(arg));
        }
        Node node = rangeFunction.call(environment, args);
        Assert.assertTrue(node instanceof ArrayNode);
        ArrayNode arrayNode = (ArrayNode) node;
        Assert.assertEquals(expected.length, arrayNode.length());
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], ((NumberNode) arrayNode.get(i)).getInteger());
        }
    }

    @Test
    public void simpleRangesWork() throws RuntimeException {
        doRangeTest(new int[]{3}, new int[]{0, 1, 2});
        doRangeTest(new int[]{1, 3}, new int[]{1, 2});
        doRangeTest(new int[]{1, 3, -1}, new int[]{3, 2});
    }
}