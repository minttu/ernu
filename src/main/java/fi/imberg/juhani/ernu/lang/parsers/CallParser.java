package fi.imberg.juhani.ernu.lang.parsers;

import fi.imberg.juhani.ernu.lang.Parser;
import fi.imberg.juhani.ernu.lang.Token;
import fi.imberg.juhani.ernu.lang.TokenType;
import fi.imberg.juhani.ernu.lang.exceptions.LangException;
import fi.imberg.juhani.ernu.lang.node.CallNode;
import fi.imberg.juhani.ernu.lang.node.Node;

import java.util.ArrayList;
import java.util.List;

public class CallParser implements InfixParser {
    @Override
    public Node parse(Parser parser, Node left, Token token) throws LangException {
        List<Node> args = new ArrayList<>();
        if(!parser.match(TokenType.RPAREN)) {
            do {
                args.add(parser.parseNode());
            } while(parser.match(TokenType.COMMA));
            parser.consume(TokenType.RPAREN);
        }
        return new CallNode(left, args);
    }

    @Override
    public int getPrecedence() {
        return 10;
    }
}
