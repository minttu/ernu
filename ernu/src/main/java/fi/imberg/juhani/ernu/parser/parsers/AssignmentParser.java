package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.interfaces.Identifier;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.AssignmentNode;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Precedence;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;
import fi.imberg.juhani.ernu.parser.exceptions.ParsingException;

/**
 * Parses an expression for assignment. For example a = b
 */
public class AssignmentParser implements InfixParser {
    @Override
    public Node parse(Parser parser, Node left, Token token) throws LangException {
        Node right = parser.parseNode(0);
        if (!(left instanceof Identifier)) {
            throw new ParsingException("Assignment left side must be an Identifier", token);
        }
        return new AssignmentNode(left, token.getType(), right);
    }

    @Override
    public int getPrecedence() {
        return Precedence.ASSIGNMENT;
    }
}
