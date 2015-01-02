package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.NullNode;
import fi.imberg.juhani.ernu.interpreter.nodes.NumberNode;
import fi.imberg.juhani.ernu.interpreter.nodes.StringNode;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrintFunctionTest {
    Environment environment;
    PrintFunction printFunction;
    ByteArrayOutputStream out;
    PrintStream originalOut;

    @Before
    public void init() {
        environment = new Environment(false, null, "test");
        printFunction = new PrintFunction();
        out = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(out));
    }

    @After
    public void destroy() {
        environment = null;
        printFunction = null;
        System.setOut(originalOut);
    }

    public void doTest(Node[] args, String expected) throws RuntimeException {
        List<Node> argsList = new ArrayList<>();
        Collections.addAll(argsList, args);
        Node node = printFunction.call(environment, argsList);
        Assert.assertTrue(node instanceof NullNode);
        Assert.assertEquals(expected + "\n", out.toString());
        out.reset();
    }

    @Test
    public void simpleTest() throws RuntimeException {
        doTest(new Node[]{new NumberNode(0)}, "0");
        doTest(new Node[]{new NumberNode(0), new NumberNode(1)}, "0, 1");
        doTest(new Node[]{new StringNode("asd"), new NullNode()}, "asd, null");
        doTest(new Node[]{}, "");
    }
}