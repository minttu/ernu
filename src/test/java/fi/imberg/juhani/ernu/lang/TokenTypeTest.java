package fi.imberg.juhani.ernu.lang;

import org.junit.Assert;
import org.junit.Test;

public class TokenTypeTest {

    public void doTest(String text, TokenType expected) {
        Token token = new Token(0, 0, text);
        TokenType.matchType(token);
        Assert.assertEquals(expected, token.getType());
    }

    public void doTest(String[] texts, TokenType[] expected) {
        for (int i = 0; i < texts.length; i++) {
            doTest(texts[i], expected[i]);
        }
    }

    @Test
    public void testSymbols() {
        String[] symbols = "== = > < >= <= != ^ + - / * ^= += -= /= *= , ( )".split(" ");
        TokenType[] expected = new TokenType[]{
                TokenType.EQ, TokenType.SET, TokenType.GT, TokenType.LT, TokenType.GTOE, TokenType.LTOE,
                TokenType.NOTEQ, TokenType.POW, TokenType.ADD, TokenType.SUB, TokenType.DIV, TokenType.MUL,
                TokenType.POWSET, TokenType.ADDSET, TokenType.SUBSET, TokenType.DIVSET, TokenType.MULSET,
                TokenType.COMMA, TokenType.LPAREN, TokenType.RPAREN};
        doTest(symbols, expected);
    }

    @Test
    public void testKeywords() {
        String[] keywords = "function if do else end return elseif match while break case".split(" ");
        TokenType[] expected = new TokenType[]{
                TokenType.FUNCTION, TokenType.IF, TokenType.DO, TokenType.ELSE, TokenType.END, TokenType.RETURN,
                TokenType.ELSEIF, TokenType.MATCH, TokenType.WHILE, TokenType.BREAK, TokenType.CASE
        };
        doTest(keywords, expected);
    }

    @Test
    public void testEmpty() {
        doTest("", TokenType.NONE);
    }

    @Test
    public void testNumbers() {
        String[] texts = "0 -1 1.0 -1.0".split(" ");
        for (String text : texts) {
            doTest(text, TokenType.NUMBER);
        }
    }
}
