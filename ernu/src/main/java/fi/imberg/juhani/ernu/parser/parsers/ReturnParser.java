package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.ReturnNode;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

/**
 * Parses an expression for a return statement. For example return 3
 */
public class ReturnParser implements PrefixParser {
    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        return new ReturnNode(parser.parseNode());
    }
}
