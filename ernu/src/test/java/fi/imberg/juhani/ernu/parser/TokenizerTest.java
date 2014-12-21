package fi.imberg.juhani.ernu.parser;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TokenizerTest {
    Tokenizer tokenizer;

    @Before
    public void init() {
        tokenizer = new Tokenizer();
    }

    @After
    public void destroy() {
        tokenizer = null;
    }

    public void doTest(String text, int[] line, int[] column, String[] content) {
        tokenizer.tokenize(text);
        for (int i = 0; i < line.length; i++) {
            Token token = tokenizer.nextToken();
            Assert.assertNotEquals(null, token);
            Assert.assertNotEquals(TokenType.NONE, token.getType());
            Assert.assertEquals(line[i], token.getLine());
            Assert.assertEquals(column[i], token.getColumn());
            Assert.assertEquals(content[i], token.getContent());
        }
        Assert.assertTrue(tokenizer.isEmpty());
    }

    @Test
    public void namesParsed() {
        doTest("hello", new int[]{0}, new int[]{0}, new String[]{"hello"});
        doTest("hello world", new int[]{0, 0}, new int[]{0, 6}, new String[]{"hello", "world"});
        doTest("hello this world", new int[]{0, 0, 0}, new int[]{0, 6, 11}, new String[]{"hello", "this", "world"});
    }

    @Test
    public void areNumbersParsed() {
        doTest("3", new int[]{0}, new int[]{0}, new String[]{"3"});
        doTest("3.14", new int[]{0}, new int[]{0}, new String[]{"3.14"});
        doTest("-3.14", new int[]{0}, new int[]{0}, new String[]{"-3.14"});
        doTest("-0 -9", new int[]{0, 0}, new int[]{0, 3}, new String[]{"-0", "-9"});
    }

    @Test
    public void numbersWithOperatorsParsed() {
        doTest("1+1", new int[]{0, 0, 0}, new int[]{0, 1, 2}, new String[]{"1", "+", "1"});
        doTest("1 + 1", new int[]{0, 0, 0}, new int[]{0, 2, 4}, new String[]{"1", "+", "1"});
        doTest("-3.14--3.13", new int[]{0, 0, 0}, new int[]{0, 5, 6}, new String[]{"-3.14", "-", "-3.13"});
    }

    @Test
    public void literalStringsParsed() {
        doTest("\"hello\"", new int[]{0}, new int[]{0}, new String[]{"\"hello\""});
        doTest("\"hello 3.14 ' world\"", new int[]{0}, new int[]{0}, new String[]{"\"hello 3.14 ' world\""});
        doTest("\"3pi\"+3.14", new int[]{0, 0, 0}, new int[]{0, 5, 6}, new String[]{"\"3pi\"", "+", "3.14"});
    }

    @Test
    public void whitespaceParsed() {
        doTest("  hello\n    world", new int[]{0, 0, 1}, new int[]{2, 7, 4}, new String[]{"hello", "\n", "world"});
    }

    @Test
    public void parenthesisParsed() {
        doTest("this(3*(that),4)",
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 4, 5, 6, 7, 8, 12, 13, 14, 15},
                new String[]{"this", "(", "3", "*", "(", "that", ")", ",", "4", ")"});
    }

    @Test
    public void operatorsParsed() {
        doTest("== != <= >= %= += -= *= /= < = > % + - * / || &&",
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 3, 6, 9, 12, 15, 18, 21, 24, 27, 29, 31, 33, 35, 37, 39, 41, 43, 46},
                new String[]{"==", "!=", "<=", ">=", "%=", "+=", "-=", "*=", "/=", "<",
                        "=", ">", "%", "+", "-", "*", "/", "||", "&&"});
        doTest("a ||", new int[]{0, 0}, new int[]{0, 2}, new String[]{"a", "||"});
        doTest("a= a||", new int[]{0, 0, 0, 0}, new int[]{0, 1, 3, 4}, new String[]{"a", "=", "a", "||"});
    }

    @Test
    public void commentsParsed() {
        doTest("# this is a comment\nhello", new int[]{0, 1}, new int[]{19, 0}, new String[]{"\n", "hello"});
    }
}
