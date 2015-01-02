package fi.imberg.juhani.ernu.parser;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TokenTest {
    Token token;

    @Before
    public void init() {
        token = new Token(0, 0, "");
    }

    @After
    public void destroy() {
        token = null;
    }

    @Test
    public void testConstructor() {
        Assert.assertEquals(0, token.getColumn());
        Assert.assertEquals(0, token.getLine());
        Assert.assertEquals(0, token.getLength());
        Assert.assertEquals("", token.getContent());
        Assert.assertEquals(TokenType.NONE, token.getType());
    }

    @Test
    public void testAddChar() {
        token.add('a');
        Assert.assertEquals(1, token.getLength());
        Assert.assertEquals("a", token.getContent());
    }

    @Test
    public void testAddString() {
        token.add("hello");
        Assert.assertEquals(5, token.getLength());
        Assert.assertEquals("hello", token.getContent());
    }

    @Test
    public void testMoveColumn() {
        token.moveColumn();
        Assert.assertEquals(1, token.getColumn());
    }

    @Test
    public void testToString() {
        Assert.assertEquals("  0:0                  NONE    ", token.toString());
        token.add('\n');
        Assert.assertEquals("  0:0                  NONE    <EOL>", token.toString());
    }

    @Test
    public void testEquals() {
        Assert.assertNotEquals(new Token(0, 1, "a"), new Token(0, 0, "a"));
        Assert.assertNotEquals(new Token(1, 0, "a"), new Token(0, 0, "a"));
        Assert.assertNotEquals(new Token(0, 0, "a"), null);
        Assert.assertEquals(token, token);
        Assert.assertEquals(token.hashCode(), token.hashCode());
        Assert.assertNotEquals(token, 0);
        token.setType(TokenType.IDENTIFIER);
        Assert.assertNotEquals(token, new Token(0, 0, ""));
    }
}
