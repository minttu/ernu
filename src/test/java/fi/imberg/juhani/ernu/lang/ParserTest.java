package fi.imberg.juhani.ernu.lang;

import fi.imberg.juhani.ernu.lang.exceptions.LangException;
import fi.imberg.juhani.ernu.lang.node.Node;
import org.junit.Assert;
import org.junit.After;
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
            while(node == null) {
                node = parser.parseNode();
            }
            Assert.assertEquals(tree, node.toString());
        } catch (LangException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void groupingWorks() {
        doTest("a ^ (b + c)", "(^ a (+ b c))");
    }

    @Test
    public void assignmentWorks() {
        doTest("a = b", "(= a b)");
    }

    @Test
    public void operatorPrecedenceWorks() {
        doTest("a = b + c * d ^ e - f / g", "(= a (- (+ b (* c (^ d e))) (/ f g)))");
    }

    @Test
    public void functionWorks() {
        doTest("a = function()\nend", "(= a (fn [] []))");
    }

    @Test
    public void ifWorks() {
        doTest("a = function()\nif c == 0 do\nend\nend", "(= a (fn [] [(if (== c 0) [] [])]))");
        doTest("a = function()\nif c == 0 do\nelse\nend\nend", "(= a (fn [] [(if (== c 0) [] [])]))");
    }

}
