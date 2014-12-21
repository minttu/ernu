package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.node.BlockNode;
import fi.imberg.juhani.ernu.interpreter.node.CaseNode;
import fi.imberg.juhani.ernu.interpreter.node.MatchNode;
import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

import java.util.ArrayList;
import java.util.List;

public class MatchParser implements PrefixParser {
    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        parser.match(TokenType.EOL);
        List<CaseNode> cases = new ArrayList<>();
        while(!parser.match(TokenType.END)) {
            parser.consume(TokenType.CASE);
            Node conditional = parser.parseNode();
            List<Node> body = new ArrayList<>();
            parser.consume(TokenType.DO);
            while(!(parser.isNext(TokenType.CASE) || parser.isNext(TokenType.END))) {
                body.add(parser.parseNode());
                parser.match(TokenType.EOL);
            }
            cases.add(new CaseNode(conditional, new BlockNode(body)));
        }
        return new MatchNode(cases);
    }
}
