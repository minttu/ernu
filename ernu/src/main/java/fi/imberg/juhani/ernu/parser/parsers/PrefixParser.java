package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

/**
 * Interface for all parsers that work from an prefix position. They are called when we have a new input.
 */
public interface PrefixParser {
    Node parse(Parser parser, Token token) throws LangException;
}
