package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.node.IntegerNode;
import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

public class NumberParser implements PrefixParser {

    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        return new IntegerNode(Integer.parseInt(token.getContent()));
    }
}
