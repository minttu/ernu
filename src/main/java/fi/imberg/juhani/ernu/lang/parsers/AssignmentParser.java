package fi.imberg.juhani.ernu.lang.parsers;

import fi.imberg.juhani.ernu.lang.Parser;
import fi.imberg.juhani.ernu.lang.Token;
import fi.imberg.juhani.ernu.lang.exceptions.LangException;
import fi.imberg.juhani.ernu.lang.exceptions.ParsingException;
import fi.imberg.juhani.ernu.lang.node.AssignmentNode;
import fi.imberg.juhani.ernu.lang.node.IdentifierNode;
import fi.imberg.juhani.ernu.lang.node.Node;

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
