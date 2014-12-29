package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.BlockNode;
import fi.imberg.juhani.ernu.interpreter.nodes.ClassNode;
import fi.imberg.juhani.ernu.interpreter.nodes.StringNode;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses an expression for a new class. For example class v = 3 end
 */
public class ClassParser implements PrefixParser {
    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        List<Node> nodes = new ArrayList<>();
        StringNode doc = new StringNode("");

        if (parser.isNext(TokenType.STRING)) {
            doc = ((StringNode) parser.parseNode());
            parser.match(TokenType.EOL);
        }

        while (!parser.match(TokenType.END)) {
            nodes.add(parser.parseNode());
            parser.match(TokenType.EOL);
        }

        return new ClassNode(doc, new BlockNode(nodes));
    }
}
