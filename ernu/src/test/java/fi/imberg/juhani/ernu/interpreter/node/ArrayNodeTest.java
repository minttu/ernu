package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ArrayNodeTest {
    Environment environment;
    ArrayNode arrayNode;

    @Before
    public void init() {
        environment = new Environment("test");
        arrayNode = new ArrayNode(new ArrayList<Node>());
    }

    @After
    public void destroy() {
        environment = null;
        arrayNode = null;
    }

    @Test
    public void setAndGetWorks() {
        Assert.assertEquals(0, arrayNode.length());
        arrayNode.set(0, new NullNode());
        Assert.assertEquals(1, arrayNode.length());
        try {
            Assert.assertEquals(new NullNode(), arrayNode.get(0));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void appendAndPrependSingleWorks() {
        arrayNode = (ArrayNode)arrayNode.append(new NumberNode(1));
        arrayNode = (ArrayNode)arrayNode.append(new NumberNode(2));
        arrayNode = (ArrayNode)arrayNode.prepend(new NumberNode(0));
        Assert.assertEquals(3, arrayNode.length());
        try {
            Assert.assertEquals(new NumberNode(0), arrayNode.get(0));
            Assert.assertEquals(new NumberNode(1), arrayNode.get(1));
            Assert.assertEquals(new NumberNode(2), arrayNode.get(2));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void appendAndPrependArrayWorks() {
        ArrayNode anotherArray = new ArrayNode(new ArrayList<Node>());
        anotherArray.set(0, new NumberNode(0));
        anotherArray.set(1, new NumberNode(1));
        anotherArray.set(2, new NumberNode(2));
        arrayNode = (ArrayNode)arrayNode.append(anotherArray);
        Assert.assertEquals(3, arrayNode.length());
        try {
            Assert.assertEquals(new NumberNode(0), arrayNode.get(0));
            Assert.assertEquals(new NumberNode(1), arrayNode.get(1));
            Assert.assertEquals(new NumberNode(2), arrayNode.get(2));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        arrayNode = (ArrayNode)arrayNode.prepend(anotherArray);
        Assert.assertEquals(6, arrayNode.length());
        try {
            Assert.assertEquals(new NumberNode(0), arrayNode.get(0));
            Assert.assertEquals(new NumberNode(1), arrayNode.get(1));
            Assert.assertEquals(new NumberNode(2), arrayNode.get(2));
            Assert.assertEquals(new NumberNode(0), arrayNode.get(3));
            Assert.assertEquals(new NumberNode(1), arrayNode.get(4));
            Assert.assertEquals(new NumberNode(2), arrayNode.get(5));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
