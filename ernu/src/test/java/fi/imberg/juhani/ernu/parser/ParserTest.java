package fi.imberg.juhani.ernu.parser;

import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;
import fi.imberg.juhani.ernu.parser.exceptions.ParsingException;
import fi.imberg.juhani.ernu.parser.exceptions.UnexpectedTokenException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParserTest {
    Tokenizer tokenizer;
    Parser parser;

    @Before
    public void init() {
        tokenizer = new Tokenizer();
        parser = new Parser(tokenizer);
    }

    @After
    public void destroy() {
        tokenizer = null;
        parser = null;
    }

    public void doTest(String source, String tree) {
        tokenizer.tokenize(source + "\n");
        try {
            Node node = parser.parseNode();
            while (node == null) {
                node = parser.parseNode();
            }
            Assert.assertEquals(tree, node.toString());
        } catch (LangException e) {
            e.printStackTrace();
        }
    }

    public void doExceptionTest(String source, Class exception) {
        tokenizer.tokenize(source + "\n");
        try {
            Node node = parser.parseNode();
            while (node == null) {
                node = parser.parseNode();
            }
            Assert.fail("Should have thrown an exception.");
        } catch (Exception e) {
            Assert.assertTrue(e.getClass().isAssignableFrom(exception));
        }
    }

    @Test
    public void throwsParsingExceptions() {
        tokenizer.tokenize("+");
        try {
            parser.parseNode();
            Assert.assertTrue(false);
        } catch (LangException ignored) {
        }

        tokenizer.tokenize("+");
        try {
            parser.consume(TokenType.IDENTIFIER);
            Assert.assertTrue(false);
        } catch (UnexpectedTokenException ignored) {
        }
    }

    @Test
    public void consumeTypeReturnsToken() {
        tokenizer.tokenize("3");
        Token token = new Token(0, 0, "3");
        TokenType.matchType(token);
        try {
            Assert.assertEquals(token, parser.consume(TokenType.NUMBER));
        } catch (LangException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void groupingWorks() {
        doTest("a % (b + c)", "(% a (+ b c))");
    }

    @Test
    public void assignmentWorks() {
        doTest("a = b", "(= a b)");
        doTest("a += b", "(+= a b)");
    }

    @Test
    public void operatorPrecedenceWorks() {
        doTest("a = b + c * d % e - f / g",
                "(= a (- (+ b (% (* c d) e)) (/ f g)))");
    }

    @Test
    public void functionWorks() {
        doTest("a = function()\nend",
                "(= a (fn [] []))");
        doTest("a = function()\n\"Doc\"\nend",
                "(= a (fn \"Doc\" [] []))");
        doTest("a = function(c, b, d) end",
                "(= a (fn [c, b, d] []))");
    }

    @Test
    public void ifWorks() {
        doTest("a = function()\nif c == 0 do\nend\nend",
                "(= a (fn [] [(if (== c 0) [] [])]))");
        doTest("a = function()\nif c == 0 do\nelse\nc += 1\nend\nend",
                "(= a (fn [] [(if (== c 0) [] [(+= c 1)])]))");
    }

    @Test
    public void forWorks() {
        doTest("a = function()\nfor i in range(100) do\nend\nend",
                "(= a (fn [] [(for i -> (range [100]) [])]))");
        doTest("for i in range(100) do print(i); print(i * 2) end",
                "(for i -> (range [100]) [(print [i]), (print [(* i 2)])])");
    }

    @Test
    public void whileWorks() {
        doTest("while i < 100 do i += 1; print(i) end",
                "(while (< i 100) [(+= i 1), (print [i])])");
        doTest("while i < 100 do end",
                "(while (< i 100) [])");
    }

    @Test
    public void matchWorks() {
        doTest("match case i == 0 do a() case i == 1 do b() end",
                "(match [((== i 0) [(a [])]), ((== i 1) [(b [])])])");
    }

    @Test
    public void returnWorks() {
        doTest("a = function()\nreturn 3\nend",
                "(= a (fn [] [(return 3)]))");
    }

    @Test
    public void callWorks() {
        doTest("a = function()\ncall()\nend",
                "(= a (fn [] [(call [])]))");
        doTest("a = function()\nfoo(0, \"bar\")\nend",
                "(= a (fn [] [(foo [0, \"bar\"])]))");
    }

    @Test
    public void arrayWorks() {
        doTest("a = function()\nb = [0, 1, 2]\nb[0] = 3\nend",
                "(= a (fn [] [(= b [0, 1, 2]), (= b[0] 3)]))");
        doTest("a = []", "(= a [])");
    }

    @Test
    public void cantAssignToNonIdentifier() {
        doExceptionTest("3 = 2", ParsingException.class);
    }

    @Test
    public void booleansWork() {
        doTest("a = true", "(= a true)");
    }

    @Test
    public void prefixesWork() {
        doTest("!true", "(! true)");
    }

}
