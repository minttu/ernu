package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.node.ArrayAccessNode;
import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.interpreter.node.NullNode;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Precedence;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

import java.util.ArrayList;
import java.util.List;


public class ArrayAccessParser implements InfixParser {
    @Override
    public Node parse(Parser parser, Node left, Token token) throws LangException {
        List<Node> args = new ArrayList<>();
        boolean addLast = false;
        while (!parser.match(TokenType.RBRACKET)) {
            addLast = false;
            if (parser.match(TokenType.SLICE_SEPARATOR)) {
                if (args.isEmpty() || args.get(args.size() - 1) instanceof NullNode) {
                    args.add(new NullNode());
                }
                addLast = true;
            } else {
                args.add(parser.parseNode());
            }
        }
        if (addLast) {
            args.add(new NullNode());
        }
        return new ArrayAccessNode(left, args);
    }

    @Override
    public int getPrecedence() {
        return Precedence.CALL;
    }
}
