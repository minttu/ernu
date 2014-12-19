package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;
import fi.imberg.juhani.ernu.parser.node.BlockNode;
import fi.imberg.juhani.ernu.parser.node.IfNode;
import fi.imberg.juhani.ernu.parser.node.Node;

import java.util.ArrayList;
import java.util.List;

public class IfParser implements PrefixParser {
    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        Node condition = parser.parseNode();
        List<Node> branch1 = new ArrayList<>();
        List<Node> branch2 = new ArrayList<>();

        parser.consume(TokenType.DO);
        parser.consume(TokenType.EOL);

        if (!parser.match(TokenType.END)) {
            do {
                if (parser.isNext(TokenType.ELSE)) {
                    break;
                }
                branch1.add(parser.parseNode());
                parser.consume(TokenType.EOL);
            } while (!parser.match(TokenType.END));
        }

        if (parser.match(TokenType.ELSE)) {
            parser.consume(TokenType.EOL);
            if (!parser.match(TokenType.END)) {
                do {
                    branch2.add(parser.parseNode());
                    parser.consume(TokenType.EOL);
                } while (!parser.match(TokenType.END));
            }
        }

        return new IfNode(condition, new BlockNode(branch1), new BlockNode(branch2));
    }
}
