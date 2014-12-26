package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IdentifierNodeTest {
    Environment environment;

    @Before
    public void init() {
        environment = new Environment("test");
    }

    @After
    public void destroy() {
        environment = null;
    }

    @Test
    public void canGetAndSet() {
        IdentifierNode identifierNode = new IdentifierNode("test");
        try {
            identifierNode.setValue(environment, new NumberNode(0));
            identifierNode.getValue(environment);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void throwsRuntimeException() {
        IdentifierNode identifierNode = new IdentifierNode("test");
        try {
            identifierNode.getValue(environment);
            Assert.fail("Should have thrown an exception.");
        } catch (RuntimeException ignored) {
        }
    }
}
