package fi.imberg.juhani.ernu.lang.parsers;

import fi.imberg.juhani.ernu.lang.Parser;
import fi.imberg.juhani.ernu.lang.Token;
import fi.imberg.juhani.ernu.lang.TokenType;
import fi.imberg.juhani.ernu.lang.exceptions.LangException;
import fi.imberg.juhani.ernu.lang.node.BlockNode;
import fi.imberg.juhani.ernu.lang.node.FunctionNode;
import fi.imberg.juhani.ernu.lang.node.Node;

import java.util.ArrayList;
import java.util.List;

public class FunctionParser implements PrefixParser {
    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        List<Node> args = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();

        parser.consume(TokenType.LPAREN);
        if(!parser.match(TokenType.RPAREN)) {
            do {
                args.add(parser.parseNode());
            } while(parser.match(TokenType.COMMA));
            parser.consume(TokenType.RPAREN);
        }

        parser.consume(TokenType.EOL);

        if(!parser.match(TokenType.END)) {
            do {
                nodes.add(parser.parseNode());
                parser.consume(TokenType.EOL);
            } while(!parser.match(TokenType.END));
        }

        //parser.consume(TokenType.EOL);

        return new FunctionNode(args, new BlockNode(nodes));
    }
}
