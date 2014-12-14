package fi.imberg.juhani.ernu.lang.parsers;

import fi.imberg.juhani.ernu.lang.Parser;
import fi.imberg.juhani.ernu.lang.Token;
import fi.imberg.juhani.ernu.lang.TokenType;
import fi.imberg.juhani.ernu.lang.exceptions.LangException;
import fi.imberg.juhani.ernu.lang.node.BlockNode;
import fi.imberg.juhani.ernu.lang.node.Node;
import fi.imberg.juhani.ernu.lang.node.WhileNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juhani on 12/14/14.
 */
public class WhileParser implements PrefixParser {
    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        Node condition = parser.parseNode();
        List<Node> branch = new ArrayList<>();
        parser.consume(TokenType.DO);
        parser.consume(TokenType.EOL);
        if(!parser.match(TokenType.END)) {
            do {
                branch.add(parser.parseNode());
                parser.consume(TokenType.EOL);
            } while(!parser.match(TokenType.END));
        }
        return new WhileNode(condition, new BlockNode(branch));
    }
}
