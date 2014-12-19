package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;
import fi.imberg.juhani.ernu.parser.node.NewArrayNode;
import fi.imberg.juhani.ernu.parser.node.Node;

import java.util.ArrayList;
import java.util.List;

public class NewArrayParser implements PrefixParser {
    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        List<Node> value = new ArrayList<>();
        if (!parser.match(TokenType.RBRACKET)) {
            do {
                value.add(parser.parseNode());
            } while (parser.match(TokenType.COMMA));
            parser.consume(TokenType.RBRACKET);
        }
        return new NewArrayNode(value);
    }
}
