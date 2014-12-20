package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.interpreter.node.ReturnNode;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

public class ReturnParser implements PrefixParser {
    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        return new ReturnNode(parser.parseNode());
    }
}
