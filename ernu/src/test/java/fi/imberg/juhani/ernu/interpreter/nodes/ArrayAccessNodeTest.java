package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayAccessNodeTest {
    Environment environment;

    @Before
    public void init() {
        environment = new Environment(false, null, "test");
    }

    @After
    public void destroy() {
        environment = null;
    }

    public void addArray(Node[] arr) {
        List<Node> nodeList = new ArrayList<>();
        Collections.addAll(nodeList, arr);
        environment.addSymbol("a", new ArrayNode(nodeList));
    }

    public Node getNode(Node[] args) throws RuntimeException {
        List<Node> nodeList = new ArrayList<>();
        Collections.addAll(nodeList, args);
        ArrayAccessNode arrayAccessNode = new ArrayAccessNode(new IdentifierNode("a"), nodeList);
        return arrayAccessNode.getValue(environment);
    }

    public void simpleTest(int i, Node other) throws RuntimeException {
        Node node = getNode(new Node[]{new NumberNode(i)});
        Assert.assertEquals(other, node);
    }

    @Test
    public void getValueSimpleWorks() throws RuntimeException {
        addArray(new Node[]{new NumberNode(0), new NullNode(), new StringNode("h")});
        simpleTest(0, new NumberNode(0));
        simpleTest(1, new NullNode());
        simpleTest(2, new StringNode("h"));
        simpleTest(-1, new StringNode("h"));
        simpleTest(-2, new NullNode());
        simpleTest(-3, new NumberNode(0));
    }

    @Test
    public void getSimpleRangeWorks() throws RuntimeException {
        addArray(new Node[]{new NumberNode(0), new NullNode(), new StringNode("h")});
        Node node = getNode(new Node[]{new NumberNode(0), new NumberNode(2)});
        Assert.assertTrue(node.getClass().isAssignableFrom(ArrayNode.class));
        ArrayNode arrayNode = (ArrayNode) node;
        Assert.assertEquals(new NumberNode(0), arrayNode.get(0));
        Assert.assertEquals(new NullNode(), arrayNode.get(1));
    }

    @Test
    public void getCopyWorks() throws RuntimeException {
        addArray(new Node[]{new NumberNode(0), new NullNode(), new StringNode("h")});
        Node node = getNode(new Node[]{new NullNode(), new NullNode()});
        Assert.assertTrue(node.getClass().isAssignableFrom(ArrayNode.class));
        ArrayNode arrayNode = (ArrayNode) node;
        Assert.assertEquals(new NumberNode(0), arrayNode.get(0));
        Assert.assertEquals(new NullNode(), arrayNode.get(1));
        Assert.assertEquals(new StringNode("h"), arrayNode.get(2));
    }

    @Test
    public void getRangeWithStepsWorks() throws RuntimeException {
        addArray(new Node[]{new NumberNode(0), new NumberNode(1), new NumberNode(2), new NumberNode(3)});
        Node node = getNode(new Node[]{new NullNode(), new NullNode(), new NumberNode(2)});
        Assert.assertTrue(node.getClass().isAssignableFrom(ArrayNode.class));
        ArrayNode arrayNode = (ArrayNode) node;
        Assert.assertEquals(new NumberNode(0), arrayNode.get(0));
        Assert.assertEquals(new NumberNode(2), arrayNode.get(1));
    }

    @Test
    public void getReverseWorks() throws RuntimeException {
        addArray(new Node[]{new NumberNode(0), new NullNode(), new StringNode("h")});
        Node node = getNode(new Node[]{new NullNode(), new NullNode(), new NumberNode(-1)});
        Assert.assertTrue(node.getClass().isAssignableFrom(ArrayNode.class));
        ArrayNode arrayNode = (ArrayNode) node;
        Assert.assertEquals(new NumberNode(0), arrayNode.get(2));
        Assert.assertEquals(new NullNode(), arrayNode.get(1));
        Assert.assertEquals(new StringNode("h"), arrayNode.get(0));
    }

    @Test
    public void getNegativeStartWorks() throws RuntimeException {
        addArray(new Node[]{new NumberNode(0), new NumberNode(1), new NumberNode(2), new NumberNode(3)});
        Node node = getNode(new Node[]{new NumberNode(-2), new NullNode()});
        Assert.assertTrue(node.getClass().isAssignableFrom(ArrayNode.class));
        ArrayNode arrayNode = (ArrayNode) node;
        Assert.assertEquals(new NumberNode(2), arrayNode.get(0));
        Assert.assertEquals(new NumberNode(3), arrayNode.get(1));
    }

    @Test
    public void getNegativeEndWorks() throws RuntimeException {
        addArray(new Node[]{new NumberNode(0), new NumberNode(1), new NumberNode(2), new NumberNode(3)});
        Node node = getNode(new Node[]{new NullNode(), new NumberNode(-2)});
        Assert.assertTrue(node.getClass().isAssignableFrom(ArrayNode.class));
        ArrayNode arrayNode = (ArrayNode) node;
        Assert.assertEquals(new NumberNode(0), arrayNode.get(0));
        Assert.assertEquals(new NumberNode(1), arrayNode.get(1));
    }

}