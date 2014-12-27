package fi.imberg.juhani.ernu.interpreter;

import fi.imberg.juhani.ernu.interpreter.builtin.*;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.BooleanNode;
import fi.imberg.juhani.ernu.interpreter.nodes.StringNode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class EnvironmentTest {
    Environment environment;

    @Before
    public void init() {
        environment = new Environment(false, null, "test");
    }

    @After
    public void destroy() {
        environment = null;
    }

    @Test
    public void testShortConstructors() throws RuntimeException {
        environment = new Environment("test");
        Node node = environment.getSymbol("__name__");
        Assert.assertEquals("test", ((StringNode) node).getStringLiteral());
        node = environment.getSymbol("__executed__");
        Assert.assertTrue(((BooleanNode) node).isTrue());
    }

    @Test
    public void testEnvironmentSpecificSymbols() throws RuntimeException {
        Node node = environment.getSymbol("__name__");
        Assert.assertEquals("test", ((StringNode) node).getStringLiteral());
        node = environment.getSymbol("__executed__");
        Assert.assertFalse(((BooleanNode) node).isTrue());
    }

    public void testGetSymbolAndMatchType(String key, Class cls) throws RuntimeException {
        Node node = environment.getSymbol(key);
        Assert.assertTrue(node.getClass().isAssignableFrom(cls));
    }

    @Test
    public void testBuiltinSymbols() throws RuntimeException {
        String[] keys = new String[]{"print", "range", "len", "help", "type", "defined", "apply", "str", "num"};
        Class[] clss = new Class[]{PrintFunction.class, RangeFunction.class, LenFunction.class, HelpFunction.class,
                TypeFunction.class, DefinedFunction.class, ApplyFunction.class, StrFunction.class, NumFunction.class};
        for(int i = 0; i < keys.length; i++) {
            testGetSymbolAndMatchType(keys[i], clss[i]);
        }
    }

    @Test
    public void testSubEnvironment() {
        Environment sub = environment.subEnvironment();
        Assert.assertEquals(environment.getFileName(), sub.getFileName());
        Assert.assertEquals(environment, sub.getParent());
    }
}