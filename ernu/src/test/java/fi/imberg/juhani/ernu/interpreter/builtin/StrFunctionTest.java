package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.ArrayNode;
import fi.imberg.juhani.ernu.interpreter.nodes.NumberNode;
import fi.imberg.juhani.ernu.interpreter.nodes.StringNode;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

public class StrFunctionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    Environment environment;
    StrFunction strFunction;

    @Before
    public void init() {
        environment = new Environment(false, null, "test");
        strFunction = new StrFunction();
    }

    @After
    public void destroy() {
        environment = null;
        strFunction = null;
    }

    @Test
    public void mustBeOneArgument() throws RuntimeException {
        thrown.expect(fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException.class);
        thrown.expectMessage("str takes one argument only");

        strFunction.call(environment, new ArrayList<>());
    }

    public void doTest(Node arg, String expected) throws RuntimeException {
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(arg);
        Node node = strFunction.call(environment, nodeList);
        Assert.assertTrue(node instanceof StringNode);
        Assert.assertEquals(expected, ((StringNode) node).getStringLiteral());
    }

    @Test
    public void simpleTests() throws RuntimeException {
        doTest(new StringNode("asd"), "asd");
        doTest(new NumberNode(3), "3");
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(new StringNode("a"));
        nodeList.add(new StringNode("s"));
        nodeList.add(new StringNode("d"));
        doTest(new ArrayNode(nodeList), "asd");
    }
}