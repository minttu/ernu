package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.parser.TokenType;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

public class OperatorNodeTest {
    Environment environment;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        environment = new Environment(false, null, "test");
    }

    @After
    public void destroy() {
        environment = null;
    }

    public void doBooleanTest(Node a, TokenType type, Node b, boolean expected) throws RuntimeException {
        OperatorNode operatorNode = new OperatorNode(a, type, b);
        Node node = operatorNode.getValue(environment);
        Assert.assertTrue(node instanceof BooleanNode);
        Assert.assertEquals(expected, ((BooleanNode) node).isTrue());
    }

    public void doNumberTest(Node a, TokenType type, Node b, int expected) throws RuntimeException {
        OperatorNode operatorNode = new OperatorNode(a, type, b);
        Node node = operatorNode.getValue(environment);
        Assert.assertTrue(node instanceof NumberNode);
        Assert.assertEquals(expected, ((NumberNode) node).getInteger());
    }

    public void doArrayTest(Node a, TokenType type, Node b, List<Node> expected) throws RuntimeException {
        OperatorNode operatorNode = new OperatorNode(a, type, b);
        Node node = operatorNode.getValue(environment);
        Assert.assertTrue(node instanceof ArrayNode);
        Assert.assertEquals(expected, ((ArrayNode) node).getValues());
    }

    @Test
    public void testCompareValue() throws RuntimeException {
        doBooleanTest(new NumberNode(0), TokenType.EQ, new NumberNode(0), true);
        doBooleanTest(new NumberNode(0), TokenType.NOTEQ, new NumberNode(1), true);
        doBooleanTest(new NumberNode(0), TokenType.LT, new NumberNode(1), true);
        doBooleanTest(new NumberNode(0), TokenType.LTOE, new NumberNode(0), true);
        doBooleanTest(new NumberNode(0), TokenType.GTOE, new NumberNode(0), true);
        doBooleanTest(new NumberNode(0), TokenType.GT, new NumberNode(-1), true);

        doBooleanTest(new NumberNode(0), TokenType.EQ, new NumberNode(1), false);
        doBooleanTest(new NumberNode(0), TokenType.NOTEQ, new NumberNode(0), false);
        doBooleanTest(new NumberNode(0), TokenType.LT, new NumberNode(-1), false);
        doBooleanTest(new NumberNode(0), TokenType.LTOE, new NumberNode(-1), false);
        doBooleanTest(new NumberNode(0), TokenType.GTOE, new NumberNode(1), false);
        doBooleanTest(new NumberNode(0), TokenType.GT, new NumberNode(1), false);
    }

    @Test
    public void testMathValue() throws RuntimeException {
        doNumberTest(new NumberNode(0), TokenType.ADD, new NumberNode(1), 1);
        doNumberTest(new NumberNode(0), TokenType.ADDSET, new NumberNode(1), 1);

        doNumberTest(new NumberNode(0), TokenType.SUB, new NumberNode(1), -1);
        doNumberTest(new NumberNode(0), TokenType.SUBSET, new NumberNode(1), -1);

        doNumberTest(new NumberNode(4), TokenType.DIV, new NumberNode(2), 2);
        doNumberTest(new NumberNode(4), TokenType.DIVSET, new NumberNode(2), 2);

        doNumberTest(new NumberNode(2), TokenType.MUL, new NumberNode(2), 4);
        doNumberTest(new NumberNode(2), TokenType.MULSET, new NumberNode(2), 4);

        doNumberTest(new NumberNode(3), TokenType.MOD, new NumberNode(2), 1);
        doNumberTest(new NumberNode(3), TokenType.MODSET, new NumberNode(2), 1);
    }

    @Test
    public void testCombinedBooleanValue() throws RuntimeException {
        doBooleanTest(new BooleanNode(true), TokenType.AND, new BooleanNode(true), true);
        doBooleanTest(new BooleanNode(true), TokenType.AND, new BooleanNode(false), false);
        doBooleanTest(new BooleanNode(true), TokenType.OR, new BooleanNode(false), true);
        doBooleanTest(new BooleanNode(true), TokenType.OR, new BooleanNode(true), true);
    }

    @Test
    public void testAppend() throws RuntimeException {
        List<Node> expected = new ArrayList<>();
        expected.add(new NumberNode(3));
        doArrayTest(new ArrayNode(new ArrayList<>()), TokenType.ADD, new NumberNode(3), expected);
        doArrayTest(new ArrayNode(new ArrayList<>()), TokenType.ADDSET, new NumberNode(3), expected);
        doArrayTest(new NumberNode(3), TokenType.ADD, new ArrayNode(new ArrayList<>()), expected);
        doArrayTest(new NumberNode(3), TokenType.ADDSET, new ArrayNode(new ArrayList<>()), expected);

    }

    @Test
    public void testNoSameClass() throws RuntimeException {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("left and right don't share a class, got: 3 and null");

        OperatorNode operatorNode = new OperatorNode(new NumberNode(3), TokenType.ADD, new NullNode());
        operatorNode.getValue(environment);
    }

    @Test
    public void testNoKnowMath() throws RuntimeException {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("left and right don't know math");

        OperatorNode operatorNode = new OperatorNode(new NullNode(), TokenType.ADD, new NullNode());
        operatorNode.getValue(environment);
    }

    @Test
    public void testNoKnowCompare() throws RuntimeException {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("left and right can't be compared");

        OperatorNode operatorNode = new OperatorNode(new NullNode(), TokenType.LT, new NullNode());
        operatorNode.getValue(environment);
    }


}