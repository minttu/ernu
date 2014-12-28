package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.ErnuException;
import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.parser.ErnuParser;
import fi.imberg.juhani.ernu.parser.Tokenizer;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassNodeTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    Environment environment;

    @Before
    public void init() {
        environment = new Environment(false, null, "test");
    }

    @After
    public void destroy() {
        environment = null;
    }

    public ClassNode createClassNode(String source) throws ErnuException {
        Tokenizer tokenizer = new Tokenizer();
        ErnuParser parser = new ErnuParser(tokenizer);
        tokenizer.tokenize(source + "\n\n");
        Node node = parser.parseNode();
        Assert.assertTrue(node instanceof ClassNode);
        node.getValue(environment);
        return (ClassNode) node;
    }

    public CallNode createCallNode(Node node, Node[] args) {
        List<Node> argsList = new ArrayList<>();
        Collections.addAll(argsList, args);
        return new CallNode(node, argsList);
    }

    public ObjectAccessNode createAccessNode(Node node, String thing) {
        return new ObjectAccessNode(node, new IdentifierNode(thing));
    }

    public void doSimpleAttributeTest(Node node, String thing, Node value) throws RuntimeException {
        ObjectAccessNode objectAccessNode = createAccessNode(node, thing);
        Node ret = objectAccessNode.getValue(environment);
        Assert.assertEquals(value, ret);
    }

    @Test
    public void simpleAttribute() throws ErnuException {
        ClassNode classNode = createClassNode("class \"test test test\"; v = 3 end");
        doSimpleAttributeTest(classNode, "v", new NumberNode(3));
        doSimpleAttributeTest(classNode, "__doc__", new StringNode("test test test"));
    }

    @Test
    public void simpleInitTest() throws ErnuException {
        ClassNode classNode = createClassNode("class v = 3 end");
        Node node = classNode.call(environment, new ArrayList<>());
        Assert.assertTrue(node instanceof ObjectNode);
        doSimpleAttributeTest(node, "v", new NumberNode(3));
    }

    @Test
    public void advancedInitTest() throws ErnuException {
        ClassNode classNode = createClassNode("class init = function (this, val) this.val = val end end");
        List<Node> args = new ArrayList<>();
        args.add(new NumberNode(4));
        Node node = classNode.call(environment, args);
        Assert.assertTrue(node instanceof ObjectNode);
        doSimpleAttributeTest(node, "val", new NumberNode(4));
    }

    @Test
    public void methodTest() throws ErnuException {
        ClassNode classNode = createClassNode("class val = 0; inc = function (this) this.val += 1 end end");
        Node node = classNode.call(environment, new ArrayList<>());
        Assert.assertTrue(node instanceof ObjectNode);
        doSimpleAttributeTest(node, "val", new NumberNode(0));
        CallNode callNode = new CallNode(createAccessNode(node, "inc"), new ArrayList<>());
        callNode.getValue(environment);
        doSimpleAttributeTest(node, "val", new NumberNode(1));
    }


}