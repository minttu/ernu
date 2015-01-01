package fi.imberg.juhani.ernu.parser;

import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;
import fi.imberg.juhani.ernu.parser.exceptions.ParsingException;
import fi.imberg.juhani.ernu.parser.exceptions.UnexpectedTokenException;
import fi.imberg.juhani.ernu.parser.parsers.InfixParser;
import fi.imberg.juhani.ernu.parser.parsers.PrefixParser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Takes a bunch of tokens and builds a tree out of them.
 */
public class Parser {
    private final Tokenizer tokenizer;
    private final ArrayList<Token> buffer;
    private final HashMap<TokenType, PrefixParser> prefixParsers;
    private final HashMap<TokenType, InfixParser> infixParsers;

    /**
     * @param tokenizer A tokenizer that has already parsed some text.
     */
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

    /**
     * @param precedence Limits when to stop parsing, if a parser with higher precedence
     *                   is encountered, the parsing is stopped.
     * @return A node which probably has some children, a small tree basically.
     * @throws LangException If an unexpected token is encountered
     */
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

    /**
     * parseNode with the precedence of 0
     *
     * @return A node which probably has some children, a small tree basically.
     * @throws LangException If an unexpected token is encountered
     */
    public Node parseNode() throws LangException {
        return parseNode(0);
    }

    /**
     * Consumes the next token if it's of the type
     *
     * @param type Token type to match against
     * @return True if the next token was of type
     */
    public boolean match(TokenType type) {
        Token token = lookAhead(0);
        if (token.getType() != type) {
            return false;
        }
        consume();
        return true;
    }

    /**
     * @param type Token type to match against
     * @return True if the next token was of type
     */
    public boolean isNext(TokenType type) {
        Token token = lookAhead(0);
        return token.getType() == type;
    }

    /**
     * @param type Token type to match against
     * @return A token that is of the type
     * @throws UnexpectedTokenException If the next token is not of the type
     */
    public Token consume(TokenType type) throws UnexpectedTokenException {
        Token token = lookAhead(0);
        if (token.getType() != type) {
            throw new UnexpectedTokenException(type, token);
        }
        return consume();
    }

    /**
     * @return The next token
     */
    public Token consume() {
        lookAhead(0);
        return buffer.remove(0);
    }

    /**
     * @return The precedence of the next tokens parser type
     */
    public int getPrecedence() {
        InfixParser parser = infixParsers.get(lookAhead(0).getType());
        if (parser != null) {
            return parser.getPrecedence();
        }
        return 0;
    }

    /**
     * @param number How many tokens are buffered from the tokenizer
     * @return The nth next token
     */
    private Token lookAhead(int number) {
        while (number >= buffer.size()) {
            buffer.add(tokenizer.nextToken());
        }
        return buffer.get(number);
    }
}
