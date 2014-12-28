package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.ImportNode;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

import java.util.ArrayList;
import java.util.List;

public class ImportParser implements PrefixParser {
    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        String what = "";
        String as = "";
        List<String> subs = new ArrayList<>();
        while (!(parser.isNext(TokenType.EOL) || parser.isNext(TokenType.AS) || parser.isNext(TokenType.IMPORT))) {
            what += parser.consume().getContent();
        }
        if (parser.match(TokenType.AS)) {
            as = parser.consume().getContent();
        } else if (parser.match(TokenType.IMPORT)) {
            do {
                subs.add(parser.consume(TokenType.IDENTIFIER).getContent());
            } while (parser.match(TokenType.COMMA));
        }
        return new ImportNode(what, as, subs);
    }
}
