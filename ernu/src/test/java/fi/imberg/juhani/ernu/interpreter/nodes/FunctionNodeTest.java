package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.ErnuException;
import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.*;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.parser.ErnuParser;
import fi.imberg.juhani.ernu.parser.Tokenizer;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunctionNodeTest {
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

    public FunctionNode createFunctionNode(String source) throws LangException {
        Tokenizer tokenizer = new Tokenizer();
        ErnuParser parser = new ErnuParser(tokenizer);
        tokenizer.tokenize(source + "\n\n");
        Node node = parser.parseNode();
        Assert.assertTrue(node instanceof FunctionNode);
        return (FunctionNode) node;
    }

    public CallNode createCallNode(FunctionNode functionNode, Node[] args) {
        List<Node> argsList = new ArrayList<>();
        Collections.addAll(argsList, args);
        return new CallNode(functionNode, argsList);
    }

    public void doTest(String source, Node[] args, Node ret) throws ErnuException {
        FunctionNode functionNode = createFunctionNode(source);
        CallNode callNode = createCallNode(functionNode, args);
        Node got = callNode.getValue(environment);
        Assert.assertEquals(ret, got);
    }

    @Test
    public void testSimpleReturn() throws ErnuException {
        doTest("function () 3 end", new Node[]{}, new NumberNode(3));
        doTest("function () return 3 end", new Node[]{}, new NumberNode(3));
        doTest("function (i) i end", new Node[]{new NumberNode(2)}, new NumberNode(2));
        doTest("function (i) i * 3 end", new Node[]{new NumberNode(2)}, new NumberNode(6));
    }

    @Test
    public void testDoc() throws ErnuException {
        FunctionNode functionNode = createFunctionNode("function () \"test test test\" end");
        Assert.assertEquals(new StringNode("test test test"), functionNode.getAttribute("__doc__"));
    }

    @Test
    public void testWrongNumberOfArguments() throws ErnuException {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Wrong number of arguments");

        FunctionNode functionNode = createFunctionNode("function (i) end");
        CallNode callNode = new CallNode(functionNode, new ArrayList<>());
        callNode.getValue(environment);
    }

    @Test
    public void testNonIdentifierInArguments() throws ErnuException {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Non identifier nodes in arguments");

        FunctionNode functionNode = createFunctionNode("function (3) end");
        CallNode callNode = createCallNode(functionNode, new Node[]{new NullNode()});
        callNode.getValue(environment);
    }

}