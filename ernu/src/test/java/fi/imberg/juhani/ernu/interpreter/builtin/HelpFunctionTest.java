package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.NullNode;
import fi.imberg.juhani.ernu.interpreter.nodes.StringNode;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

public class HelpFunctionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    Environment environment;
    HelpFunction helpFunction;

    @Before
    public void init() {
        environment = new Environment(false, null, "test");
        helpFunction = new HelpFunction();
    }

    @After
    public void destroy() {
        environment = null;
        helpFunction = null;
    }

    @Test
    public void requiresOneArgument() throws RuntimeException {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("help takes one argument only");

        helpFunction.call(environment, new ArrayList<>());
    }

    @Test
    public void requiresAObject() throws RuntimeException {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("helps argument must be an object");

        List<Node> nodeList = new ArrayList<>();
        nodeList.add(new NullNode());

        helpFunction.call(environment, nodeList);
    }

    @Test
    public void works() throws RuntimeException {
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(helpFunction);

        Node node = helpFunction.call(environment, nodeList);
        Assert.assertTrue(node instanceof StringNode);
        Assert.assertEquals("Helps with something.", ((StringNode) node).getStringLiteral());
    }
}