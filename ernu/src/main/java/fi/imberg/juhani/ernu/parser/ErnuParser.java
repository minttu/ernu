package fi.imberg.juhani.ernu.parser;

import fi.imberg.juhani.ernu.parser.parsers.*;

public class ErnuParser extends Parser {
    public ErnuParser(Tokenizer tokenizer) {
        super(tokenizer);

        addPrefixParser(TokenType.IDENTIFIER, new IdentifierParser());
        addPrefixParser(TokenType.NUMBER, new NumberParser());
        addPrefixParser(TokenType.LPAREN, new GroupingParser());
        addPrefixParser(TokenType.FUNCTION, new FunctionParser());
        addPrefixParser(TokenType.STRING, new StringParser());
        addPrefixParser(TokenType.IF, new IfParser());
        addPrefixParser(TokenType.WHILE, new WhileParser());
        addPrefixParser(TokenType.FOR, new ForParser());
        addPrefixParser(TokenType.MATCH, new MatchParser());
        addPrefixParser(TokenType.CLASS, new ClassParser());
        addPrefixParser(TokenType.RETURN, new ReturnParser());
        addPrefixParser(TokenType.LBRACKET, new ArrayParser());
        addPrefixParser(TokenType.TRUE, new BooleanParser());
        addPrefixParser(TokenType.FALSE, new BooleanParser());
        addPrefixParser(TokenType.IMPORT, new ImportParser());
        addPrefixParser(TokenType.FROM, new ImportParser());

        addPrefixParser(TokenType.NOT, new PrefixOperatorParser());
        addPrefixParser(TokenType.SUB, new PrefixOperatorParser());

        addInfixParser(TokenType.LPAREN, new CallParser());
        addInfixParser(TokenType.LBRACKET, new ArrayAccessParser());
        addInfixParser(TokenType.OBJECT_ACCESS, new ObjectAccessParser());

        addInfixParser(TokenType.SET, new AssignmentParser());
        addInfixParser(TokenType.ADDSET, new AssignmentParser());
        addInfixParser(TokenType.SUBSET, new AssignmentParser());
        addInfixParser(TokenType.MULSET, new AssignmentParser());
        addInfixParser(TokenType.DIVSET, new AssignmentParser());
        addInfixParser(TokenType.MODSET, new AssignmentParser());

        addInfixParser(TokenType.OR, new OperatorParser(Precedence.OR, false));
        addInfixParser(TokenType.AND, new OperatorParser(Precedence.AND, false));

        addInfixParser(TokenType.EQ, new OperatorParser(Precedence.EQUALITY, false));
        addInfixParser(TokenType.NOTEQ, new OperatorParser(Precedence.EQUALITY, false));

        addInfixParser(TokenType.GT, new OperatorParser(Precedence.COMPARISON, false));
        addInfixParser(TokenType.LT, new OperatorParser(Precedence.COMPARISON, false));
        addInfixParser(TokenType.GTOE, new OperatorParser(Precedence.COMPARISON, false));
        addInfixParser(TokenType.LTOE, new OperatorParser(Precedence.COMPARISON, false));

        addInfixParser(TokenType.ADD, new OperatorParser(Precedence.SUM, false));
        addInfixParser(TokenType.SUB, new OperatorParser(Precedence.SUM, false));
        addInfixParser(TokenType.DIV, new OperatorParser(Precedence.PRODUCT, false));
        addInfixParser(TokenType.MUL, new OperatorParser(Precedence.PRODUCT, false));
        addInfixParser(TokenType.MOD, new OperatorParser(Precedence.PRODUCT, false));
    }
}
