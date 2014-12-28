package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

public class ImportNodeTest {
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

    public void hasSymbol(String string, Class cls) throws RuntimeException {
        Node node = environment.getSymbol(string);
        Assert.assertTrue(node.getClass().isAssignableFrom(cls));
    }

    @Test
    public void testImportSimple() throws RuntimeException {
        ImportNode importNode = new ImportNode("math", "", new ArrayList<>());
        importNode.getValue(environment);
        hasSymbol("math", EnvironmentNode.class);
    }

    @Test
    public void testImportSimpleAs() throws RuntimeException {
        ImportNode importNode = new ImportNode("math", "m", new ArrayList<>());
        importNode.getValue(environment);
        hasSymbol("m", EnvironmentNode.class);
    }

    @Test
    public void testImportSub() throws RuntimeException {
        List<String> subs = new ArrayList<>();
        subs.add("sqrt");
        subs.add("pow");
        ImportNode importNode = new ImportNode("math", "", subs);
        importNode.getValue(environment);
        hasSymbol("sqrt", ProxyNode.class);
        hasSymbol("pow", ProxyNode.class);
    }

    @Test
    public void throwsNoSuchFile() throws RuntimeException {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("No such file as tfsne");

        ImportNode importNode = new ImportNode("tfsne", "", new ArrayList<>());
        importNode.getValue(environment);
    }


}