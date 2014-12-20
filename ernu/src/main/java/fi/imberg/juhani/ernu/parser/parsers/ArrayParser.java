package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.node.ArrayNode;
import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

import java.util.ArrayList;
import java.util.List;

public class ArrayParser implements PrefixParser {
    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        List<Node> value = new ArrayList<>();
        if (!parser.match(TokenType.RBRACKET)) {
            do {
                value.add(parser.parseNode());

            } while (parser.match(TokenType.COMMA));
            parser.consume(TokenType.RBRACKET);
        }
        return new ArrayNode(value);
    }
}
