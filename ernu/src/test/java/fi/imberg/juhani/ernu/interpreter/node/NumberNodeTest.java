package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.parser.TokenType;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

public class NumberNodeTest {

    public void bigIntegerTest(NumberNode node, BigInteger numerator, BigInteger denominator) {
        Assert.assertEquals(numerator, node.getNumerator());
        Assert.assertEquals(denominator, node.getDenominator());
    }

    public void mathTest(NumberNode node1, TokenType type, NumberNode node2, NumberNode node3) {
        OperatorNode operatorNode = new OperatorNode(node1, type, node2);
        Environment environment = new Environment("test");
        try {
            Assert.assertEquals(operatorNode.getValue(environment), node3);
        } catch (fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createFromInteger() {
        bigIntegerTest(new NumberNode(1), BigInteger.ONE, BigInteger.ONE);
        bigIntegerTest(new NumberNode(90), new BigInteger("90"), BigInteger.ONE);
    }

    @Test
    public void createFromDecimal() {
        bigIntegerTest(new NumberNode("0.25"), BigInteger.ONE, new BigInteger("4"));
        bigIntegerTest(new NumberNode("0.9054054"), new BigInteger("4527027"), new BigInteger("5000000"));
    }

    @Test
    public void mathWorks() {
        mathTest(new NumberNode(1), TokenType.ADD, new NumberNode(1), new NumberNode(2));
        mathTest(new NumberNode(1), TokenType.SUB, new NumberNode(1), new NumberNode(0));
        mathTest(new NumberNode(3), TokenType.MUL, new NumberNode(2), new NumberNode(6));
        mathTest(new NumberNode(4), TokenType.DIV, new NumberNode(2), new NumberNode(2));
        mathTest(new NumberNode(10), TokenType.MOD, new NumberNode(3), new NumberNode(1));
    }

    @Test
    public void toStringWorks() {
        Assert.assertEquals("0.323232", new NumberNode("0.323232").toString());
        Assert.assertEquals("1", new NumberNode("1").toString());
    }

    @Test
    public void equalsWorks() {
        NumberNode node = new NumberNode(1);
        Assert.assertTrue(node.equals(node));
        Assert.assertFalse(new NumberNode(1).equals(new Integer(10)));
        Assert.assertTrue(new NumberNode("0.25").equals(new NumberNode(1).div(new NumberNode(4))));
        Assert.assertTrue(new NumberNode(13).div(new NumberNode(27)).equals(new NumberNode(13).div(new NumberNode(27))));
    }

    @Test
    public void compareToWorks() {
        Assert.assertTrue(new NumberNode("0.00000000000000000000000000000000000000001").compareTo(new NumberNode("0.00000000000000000000000000000000000000002")) < 0);
    }
}
