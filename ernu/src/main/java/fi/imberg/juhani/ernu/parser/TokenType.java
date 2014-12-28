package fi.imberg.juhani.ernu.parser;

/**
 * Enum of all of the possible token types
 */
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
    CLASS("class"),
    IMPORT("import"),
    FROM("from"),
    AS("as"),
    TRUE("true"),
    FALSE("false"),
    AND("and|\\&\\&"),
    OR("or|\\|\\|"),
    NOT("not|\\!"),
    IDENTIFIER("[A-Za-z_]+[A-Za-z0-9_\\.]*[?\\']{0,1}"),
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
    OBJECT_ACCESS("\\."),
    SLICE_SEPARATOR("\\:"),
    NONE(""),
    EOF("");

    private String match;

    TokenType(String match) {
        this.match = match;
    }

    /**
     * Finds the first suitable type for the token, and marks the token as being of the found type.
     * @param token The token to be matched against.
     */
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
