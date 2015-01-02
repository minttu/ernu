package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.ArrayNode;
import fi.imberg.juhani.ernu.interpreter.nodes.NullNode;
import fi.imberg.juhani.ernu.interpreter.nodes.NumberNode;
import fi.imberg.juhani.ernu.interpreter.nodes.StringNode;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

public class ApplyFunctionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    Environment environment;
    ApplyFunction applyFunction;

    @Before
    public void init() {
        environment = new Environment(false, null, "test");
        applyFunction = new ApplyFunction();
    }

    @After
    public void destroy() {
        environment = null;
        applyFunction = null;
    }

    @Test
    public void mustGetTwoArguments() throws RuntimeException {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("apply takes two arguments");

        applyFunction.call(environment, new ArrayList<>());
    }

    @Test
    public void firstArgumentMustBeAFunction() throws RuntimeException {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("applys first argument must be a function");

        List<Node> args = new ArrayList<>();
        args.add(new NullNode());
        args.add(new NullNode());

        applyFunction.call(environment, args);
    }

    @Test
    public void secondArgumentMustBeAList() throws RuntimeException {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("applys second argument must be an array");

        List<Node> args = new ArrayList<>();
        args.add(new LenFunction());
        args.add(new NullNode());

        applyFunction.call(environment, args);
    }

    @Test
    public void works() throws RuntimeException {
        List<Node> args = new ArrayList<>();
        args.add(new StrFunction());
        ArrayNode arrayNode = new ArrayNode();
        arrayNode = (ArrayNode) arrayNode.append(new NumberNode(0));
        args.add(arrayNode);

        Node node = applyFunction.call(environment, args);
        Assert.assertTrue(node instanceof StringNode);
        Assert.assertEquals("0", ((StringNode) node).getStringLiteral());
    }
}