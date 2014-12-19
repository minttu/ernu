package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;
import fi.imberg.juhani.ernu.parser.node.BlockNode;
import fi.imberg.juhani.ernu.parser.node.Node;
import fi.imberg.juhani.ernu.parser.node.WhileNode;

import java.util.ArrayList;
import java.util.List;

public class WhileParser implements PrefixParser {
    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        Node condition = parser.parseNode();
        List<Node> body = new ArrayList<>();
        parser.consume(TokenType.DO);
        parser.consume(TokenType.EOL);
        if (!parser.match(TokenType.END)) {
            do {
                body.add(parser.parseNode());
                parser.consume(TokenType.EOL);
            } while (!parser.match(TokenType.END));
        }
        return new WhileNode(condition, new BlockNode(body));
    }
}
