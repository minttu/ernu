package fi.imberg.juhani.ernu.parser;

public enum TokenType {
    LPAREN("\\("),
    RPAREN("\\)"),
    LBRACKET("\\["),
    RBRACKET("\\]"),
    NUMBER("\\-?[0-9]+\\.?[0-9]*"),
    FUNCTION("function"),
    IF("if"),
    DO("do"),
    ELSE("else"),
    END("end"),
    RETURN("return"),
    ELSEIF("elseif"),
    MATCH("match"),
    WHILE("while"),
    BREAK("break"),
    CASE("case"),
    FOR("for"),
    IN("in"),
    TRUE("true"),
    FALSE("false"),
    AND("and|\\&\\&"),
    OR("or|\\|\\|"),
    NOT("not|\\!"),
    IDENTIFIER("[A-Za-z]+[A-Za-z0-9_]*"),
    STRING("\".+\""),
    EOL("\n|\\;"),
    EQ("=="),
    SET("="),
    GT("\\>"),
    LT("\\<"),
    GTOE("\\>="),
    LTOE("\\<="),
    NOTEQ("\\!="),
    MOD("%"),
    ADD("\\+"),
    SUB("\\-"),
    DIV("\\/"),
    MUL("\\*"),
    MODSET("%="),
    ADDSET("\\+="),
    SUBSET("\\-="),
    DIVSET("\\/="),
    MULSET("\\*="),
    COMMA(","),
    NONE(""),
    EOF("");

    private String match;

    TokenType(String match) {
        this.match = match;
    }

    public static void matchType(Token token) {
        if (token.getType() != TokenType.NONE) {
            return;
        }
        String content = token.getContent();
        for (TokenType ttype : values()) {
            if (content.matches(ttype.getMatch())) {
                token.setType(ttype);
                return;
            }
        }
    }

    public String getMatch() {
        return match;
    }
}
