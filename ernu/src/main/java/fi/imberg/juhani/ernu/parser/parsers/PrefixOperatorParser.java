package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.interpreter.node.PrefixNode;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Precedence;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

public class PrefixOperatorParser implements PrefixParser {
    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        return new PrefixNode(token.getType(), parser.parseNode(Precedence.PREFIX));
    }
}
