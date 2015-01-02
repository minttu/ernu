package fi.imberg.juhani.ernu.parser;

import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;
import fi.imberg.juhani.ernu.parser.exceptions.ParsingException;
import fi.imberg.juhani.ernu.parser.exceptions.UnexpectedTokenException;
import org.junit.*;
import org.junit.rules.ExpectedException;

public class ErnuParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    Tokenizer tokenizer;
    ErnuParser parser;

    @Before
    public void init() {
        tokenizer = new Tokenizer();
        parser = new ErnuParser(tokenizer);
    }

    @After
    public void destroy() {
        tokenizer = null;
        parser = null;
    }

    public void doTest(String source, String tree) throws LangException {
        tokenizer.tokenize(source + "\n");
        Node node = parser.parseNode();
        while (node == null) {
            node = parser.parseNode();
        }
        Assert.assertEquals(tree, node.toString());
    }

    @Test
    public void throwsParsingExceptions1() throws LangException {
        thrown.expect(LangException.class);

        tokenizer.tokenize("+");
        parser.parseNode();
    }

    @Test
    public void throwsParsingExceptions2() throws LangException {
        thrown.expect(LangException.class);

        tokenizer.tokenize("+");
        parser.consume(TokenType.IDENTIFIER);
    }

    @Test
    public void consumeTypeReturnsToken() throws UnexpectedTokenException {
        tokenizer.tokenize("3");
        Token token = new Token(0, 0, "3");
        TokenType.matchType(token);
        Assert.assertEquals(token, parser.consume(TokenType.NUMBER));
    }

    @Test
    public void groupingWorks() throws LangException {
        doTest("a % (b + c)", "(% a (+ b c))");
    }

    @Test
    public void assignmentWorks() throws LangException {
        doTest("a = b", "(= a b)");
        doTest("a += b", "(+= a b)");
        doTest("a -= b", "(-= a b)");
        doTest("a *= b", "(*= a b)");
        doTest("a /= b", "(/= a b)");
        doTest("a %= b", "(%= a b)");
    }

    @Test
    public void operatorPrecedenceWorks() throws LangException {
        doTest("a = b + c * d % e - f / g",
                "(= a (- (+ b (% (* c d) e)) (/ f g)))");
    }

    @Test
    public void functionWorks() throws LangException {
        doTest("a = function()\nend",
                "(= a (fn [] []))");
        doTest("a = function()\n\"Doc\"\nend",
                "(= a (fn \"Doc\" [] []))");
        doTest("a = function(c, b, d) end",
                "(= a (fn [c, b, d] []))");
    }

    @Test
    public void ifWorks() throws LangException {
        doTest("a = function()\nif c == 0 do\nend\nend",
                "(= a (fn [] [(if (== c 0) [] [])]))");
        doTest("a = function()\nif c == 0 do\nelse\nc += 1\nend\nend",
                "(= a (fn [] [(if (== c 0) [] [(+= c 1)])]))");
        doTest("if c == 0 do v(); v() else v(); v() end",
                "(if (== c 0) [(v []), (v [])] [(v []), (v [])])");
        doTest("if c == 0 do v() else end",
                "(if (== c 0) [(v [])] [])");
        doTest("if c == 0 do v(); v(); end",
                "(if (== c 0) [(v []), (v [])] [])");
    }

    @Test
    public void forWorks() throws LangException {
        doTest("a = function()\nfor i in range(100) do\nend\nend",
                "(= a (fn [] [(for i -> (range [100]) [])]))");
        doTest("for i in range(100) do print(i); print(i * 2) end",
                "(for i -> (range [100]) [(print [i]), (print [(* i 2)])])");
    }

    @Test
    public void whileWorks() throws LangException {
        doTest("while i < 100 do i += 1; print(i) end",
                "(while (< i 100) [(+= i 1), (print [i])])");
        doTest("while i < 100 do end",
                "(while (< i 100) [])");
    }

    @Test
    public void matchWorks() throws LangException {
        doTest("match case i == 0 do a() case i == 1 do b() end",
                "(match [(case (== i 0) [(a [])]), (case (== i 1) [(b [])])])");
    }

    @Test
    public void returnWorks() throws LangException {
        doTest("a = function()\nreturn 3\nend",
                "(= a (fn [] [(return 3)]))");
    }

    @Test
    public void callWorks() throws LangException {
        doTest("a = function()\ncall()\nend",
                "(= a (fn [] [(call [])]))");
        doTest("a = function()\nfoo(0, \"bar\")\nend",
                "(= a (fn [] [(foo [0, \"bar\"])]))");
    }

    @Test
    public void arrayWorks() throws LangException {
        doTest("a = function()\nb = [0, 1, 2]\nb[0] = 3\nend",
                "(= a (fn [] [(= b [0, 1, 2]), (= b[[0]] 3)]))");
        doTest("a = []", "(= a [])");
        doTest("c = a[0:3:1]", "(= c a[[0, 3, 1]])");
        doTest("c = a[::1]", "(= c a[[null, null, 1]])");
        doTest("a[1:]", "a[[1, null]]");
    }

    @Test
    public void cantAssignToNonIdentifier() throws LangException {
        thrown.expect(ParsingException.class);

        doTest("3 = 2", "(= 3 2)");
    }

    @Test
    public void classWorks() throws LangException {
        // Classes aren't evaluated here yet
        doTest("class \"hello doc\"; v = 3 end",
                "(class [])");
        doTest("class end",
                "(class [])");
    }

    @Test
    public void importWorks() throws LangException {
        doTest("import a",
                "(import a)");
        doTest("import asd.asd",
                "(import asd.asd as asd)");
        doTest("import a as b",
                "(import a as b)");
        doTest("from a import b",
                "(from a import [b])");
        doTest("from a import b, c",
                "(from a import [b, c])");
    }

    @Test
    public void objectAccessWorks() throws LangException {
        doTest("a.b",
                "a.b");
    }

    @Test
    public void booleansWork() throws LangException {
        doTest("true", "true");
        doTest("false", "false");
    }

    @Test
    public void prefixOperatorsWork() throws LangException {
        doTest("-a", "(- a)");
        doTest("!a", "(! a)");
        doTest("not true", "(! true)");
    }

    @Test
    public void combinedTruthWorks() throws LangException {
        doTest("true and false", "(&& true false)");
        doTest("true && false", "(&& true false)");
        doTest("true or false", "(|| true false)");
        doTest("true || false", "(|| true false)");
    }

}
