package fi.imberg.juhani.ernu.parser;

import fi.imberg.juhani.ernu.interpreter.node.Node;
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

        prefixParsers.put(TokenType.IDENTIFIER, new IdentifierParser());
        prefixParsers.put(TokenType.NUMBER, new NumberParser());
        prefixParsers.put(TokenType.LPAREN, new GroupingParser());
        prefixParsers.put(TokenType.FUNCTION, new FunctionParser());
        prefixParsers.put(TokenType.STRING, new StringParser());
        prefixParsers.put(TokenType.IF, new IfParser());
        prefixParsers.put(TokenType.WHILE, new WhileParser());
        prefixParsers.put(TokenType.FOR, new ForParser());
        prefixParsers.put(TokenType.MATCH, new MatchParser());
        prefixParsers.put(TokenType.RETURN, new ReturnParser());
        prefixParsers.put(TokenType.LBRACKET, new ArrayParser());
        prefixParsers.put(TokenType.TRUE, new BooleanParser());
        prefixParsers.put(TokenType.FALSE, new BooleanParser());

        prefixParsers.put(TokenType.NOT, new PrefixOperatorParser());

        infixParsers.put(TokenType.LPAREN, new CallParser());
        infixParsers.put(TokenType.LBRACKET, new ArrayAccessParser());

        infixParsers.put(TokenType.SET, new AssignmentParser());
        infixParsers.put(TokenType.ADDSET, new AssignmentParser());
        infixParsers.put(TokenType.SUBSET, new AssignmentParser());
        infixParsers.put(TokenType.MULSET, new AssignmentParser());
        infixParsers.put(TokenType.DIVSET, new AssignmentParser());
        infixParsers.put(TokenType.MODSET, new AssignmentParser());

        infixParsers.put(TokenType.OR, new OperatorParser(Precedence.OR, false));
        infixParsers.put(TokenType.AND, new OperatorParser(Precedence.AND, false));

        infixParsers.put(TokenType.EQ, new OperatorParser(Precedence.EQUALITY, false));
        infixParsers.put(TokenType.NOTEQ, new OperatorParser(Precedence.EQUALITY, false));

        infixParsers.put(TokenType.GT, new OperatorParser(Precedence.COMPARISON, false));
        infixParsers.put(TokenType.LT, new OperatorParser(Precedence.COMPARISON, false));
        infixParsers.put(TokenType.GTOE, new OperatorParser(Precedence.COMPARISON, false));
        infixParsers.put(TokenType.LTOE, new OperatorParser(Precedence.COMPARISON, false));

        infixParsers.put(TokenType.ADD, new OperatorParser(Precedence.SUM, false));
        infixParsers.put(TokenType.SUB, new OperatorParser(Precedence.SUM, false));
        infixParsers.put(TokenType.DIV, new OperatorParser(Precedence.PRODUCT, false));
        infixParsers.put(TokenType.MUL, new OperatorParser(Precedence.PRODUCT, false));
        infixParsers.put(TokenType.MOD, new OperatorParser(Precedence.PRODUCT, false));

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
