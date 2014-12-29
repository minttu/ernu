package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.BlockNode;
import fi.imberg.juhani.ernu.interpreter.nodes.ForNode;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses an expression for a for statement. For example for i in range(100) do print(i) end
 */
public class ForParser implements PrefixParser {
    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        Node identifier = parser.parseNode();
        parser.consume(TokenType.IN);
        Node function = parser.parseNode();
        parser.consume(TokenType.DO);
        parser.match(TokenType.EOL);

        List<Node> body = new ArrayList<>();
        if (!parser.match(TokenType.END)) {
            do {
                body.add(parser.parseNode());
                parser.match(TokenType.EOL);
            } while (!parser.match(TokenType.END));
        }

        return new ForNode(identifier, function, new BlockNode(body));
    }
}
