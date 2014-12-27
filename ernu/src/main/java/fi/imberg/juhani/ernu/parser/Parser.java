package fi.imberg.juhani.ernu.parser;

import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;
import fi.imberg.juhani.ernu.parser.exceptions.ParsingException;
import fi.imberg.juhani.ernu.parser.exceptions.UnexpectedTokenException;
import fi.imberg.juhani.ernu.parser.parsers.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
    private final Tokenizer tokenizer;
    private final ArrayList<Token> buffer;
    private final HashMap<TokenType, PrefixParser> prefixParsers;
    private final HashMap<TokenType, InfixParser> infixParsers;

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        this.buffer = new ArrayList<>();
        this.prefixParsers = new HashMap<>();
        this.infixParsers = new HashMap<>();
    }

    public void addInfixParser(TokenType tokenType, InfixParser parser) {
        this.infixParsers.put(tokenType, parser);
    }

    public void addPrefixParser(TokenType tokenType, PrefixParser parser) {
        this.prefixParsers.put(tokenType, parser);
    }

    public Node parseNode(int precedence) throws LangException {
        Token current = consume();
        if (current.getType() == TokenType.EOL) {
            return null;
        }
        PrefixParser prefixParser = prefixParsers.get(current.getType());
        if (prefixParser == null) {
            throw new ParsingException("Unexpected token.", current);
        }
        Node left = prefixParser.parse(this, current);
        while (precedence < getPrecedence()) {
            current = consume();
            InfixParser infixParser = infixParsers.get(current.getType());
            left = infixParser.parse(this, left, current);
        }
        return left;
    }

    public Node parseNode() throws LangException {
        return parseNode(0);
    }

    public boolean match(TokenType type) {
        Token token = lookAhead(0);
        if (token.getType() != type) {
            return false;
        }
        consume();
        return true;
    }

    public boolean isNext(TokenType type) {
        Token token = lookAhead(0);
        return token.getType() == type;
    }

    public Token consume(TokenType type) throws UnexpectedTokenException {
        Token token = lookAhead(0);
        if (token.getType() != type) {
            throw new UnexpectedTokenException(type, token);
        }
        return consume();
    }

    public Token consume() {
        lookAhead(0);
        return buffer.remove(0);
    }

    public int getPrecedence() {
        InfixParser parser = infixParsers.get(lookAhead(0).getType());
        if (parser != null) {
            return parser.getPrecedence();
        }
        return 0;
    }

    private Token lookAhead(int number) {
        while (number >= buffer.size()) {
            buffer.add(tokenizer.nextToken());
        }
        return buffer.get(number);
    }
}
