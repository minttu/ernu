package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;
import fi.imberg.juhani.ernu.parser.exceptions.ParsingException;
import fi.imberg.juhani.ernu.parser.node.AssignmentNode;
import fi.imberg.juhani.ernu.parser.node.IdentifierNode;
import fi.imberg.juhani.ernu.parser.node.Node;

public class AssignmentParser implements InfixParser {
    @Override
    public Node parse(Parser parser, Node left, Token token) throws LangException {
        Node right = parser.parseNode(0);
        if(!(left instanceof IdentifierNode)) {
            throw new ParsingException("Assignment left side must be an Identifier", token);
        }
        return new AssignmentNode((IdentifierNode)left, token.getType(), right);
    }

    @Override
    public int getPrecedence() {
        return 1;
    }
}
