package fi.imberg.juhani.ernu.parser;

import fi.imberg.juhani.ernu.parser.exceptions.LangException;
import fi.imberg.juhani.ernu.interpreter.node.Node;
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
        doTest("a += b", "(+= a b)");
    }

    @Test
    public void operatorPrecedenceWorks() {
        doTest("a = b + c * d ^ e - f / g", "(= a (- (+ b (* c (^ d e))) (/ f g)))");
    }

    @Test
    public void functionWorks() {
        doTest("a = function()\nend", "(= a (fn [] []))");
        doTest("a = function()\n\"Doc\"\nend", "(= a (fn \"Doc\" [] []))");
    }

    @Test
    public void ifWorks() {
        doTest("a = function()\nif c == 0 do\nend\nend", "(= a (fn [] [(if (== c 0) [] [])]))");
        doTest("a = function()\nif c == 0 do\nelse\nc += 1\nend\nend", "(= a (fn [] [(if (== c 0) [] [(+= c 1)])]))");
    }

    @Test
    public void forWorks() {
        doTest("a = function()\nfor i in range(100) do\nend\nend", "(= a (fn [] [(for i -> (range [100]) [])]))");
    }

    @Test
    public void whileWorks() {
        doTest("a = function()\nwhile i < 100 do\ni += 1\nend\nend", "(= a (fn [] [(while (< i 100) [(+= i 1)])]))");
    }

    @Test
    public void returnWorks() {
        doTest("a = function()\nreturn 3\nend", "(= a (fn [] [(return 3)]))");
    }

    @Test
    public void callWorks() {
        doTest("a = function()\ncall()\nend", "(= a (fn [] [(call [])]))");
        doTest("a = function()\nfoo(0, \"bar\")\nend", "(= a (fn [] [(foo [0, \"bar\"])]))");
    }

    @Test
    public void arrayWorks() {
        doTest("a = function()\nb = [0, 1, 2]\nb[0] = 3\nend", "(= a (fn [] [(= b [0, 1, 2]), (= b[0] 3)]))");
    }

}
