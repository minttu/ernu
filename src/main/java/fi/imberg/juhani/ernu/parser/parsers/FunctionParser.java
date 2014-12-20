package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.node.BlockNode;
import fi.imberg.juhani.ernu.interpreter.node.FunctionNode;
import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.interpreter.node.StringNode;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

import java.util.ArrayList;
import java.util.List;

public class FunctionParser implements PrefixParser {
    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        List<Node> args = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        String doc = "";

        parser.consume(TokenType.LPAREN);
        if (!parser.match(TokenType.RPAREN)) {
            do {
                args.add(parser.parseNode());
            } while (parser.match(TokenType.COMMA));
            parser.consume(TokenType.RPAREN);
        }

        parser.match(TokenType.EOL);

        if (parser.isNext(TokenType.STRING)) {
            doc = ((StringNode) parser.parseNode()).getStringLiteral();
            parser.match(TokenType.EOL);
        }

        if (!parser.match(TokenType.END)) {
            do {
                nodes.add(parser.parseNode());
                parser.match(TokenType.EOL);
            } while (!parser.match(TokenType.END));
        }

        return new FunctionNode(args, doc, new BlockNode(nodes));
    }
}
