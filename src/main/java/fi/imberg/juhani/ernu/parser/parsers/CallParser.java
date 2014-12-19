package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.node.CallNode;
import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

import java.util.ArrayList;
import java.util.List;

public class CallParser implements InfixParser {
    @Override
    public Node parse(Parser parser, Node left, Token token) throws LangException {
        List<Node> args = new ArrayList<>();
        if (!parser.match(TokenType.RPAREN)) {
            do {
                args.add(parser.parseNode());
            } while (parser.match(TokenType.COMMA));
            parser.consume(TokenType.RPAREN);
        }
        return new CallNode(left, args);
    }

    @Override
    public int getPrecedence() {
        return 10;
    }
}
