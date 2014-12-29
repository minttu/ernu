package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.BlockNode;
import fi.imberg.juhani.ernu.interpreter.nodes.CaseNode;
import fi.imberg.juhani.ernu.interpreter.nodes.MatchNode;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses an expression for a match statement. For example match case i % 2 == 0 do print(i) end
 */
public class MatchParser implements PrefixParser {
    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        parser.match(TokenType.EOL);
        List<CaseNode> cases = new ArrayList<>();
        while (!parser.match(TokenType.END)) {
            parser.consume(TokenType.CASE);
            Node conditional = parser.parseNode();
            List<Node> body = new ArrayList<>();
            parser.consume(TokenType.DO);
            while (!(parser.isNext(TokenType.CASE) || parser.isNext(TokenType.END))) {
                body.add(parser.parseNode());
                parser.match(TokenType.EOL);
            }
            cases.add(new CaseNode(conditional, new BlockNode(body)));
        }
        return new MatchNode(cases);
    }
}
