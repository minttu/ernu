package fi.imberg.juhani.ernu.parser;

/**
 * Describes a token, a piece of text that can be grouped.
 */
public class Token {
    private int line;
    private int column;
    private int length;
    private String content;
    private TokenType type;

    /**
     *
     * @param line The line number the token starts appearing on
     * @param column The column the token starts appearing on
     * @param content The content of the token
     */
    public Token(int line, int column, String content) {
        this.line = line;
        this.column = column;
        this.length = content.length();
        this.content = content;
        this.type = TokenType.NONE;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getLength() {
        return length;
    }

    public String getContent() {
        return content;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    /**
     * Adds a character to the token.
     * @param c The character to be added
     */
    public void add(char c) {
        length++;
        content += c;
    }

    /**
     * Adds a string to the token.
     * @param chars The string to be added
     */
    public void add(String chars) {
        for (char c : chars.toCharArray()) {
            add(c);
        }
    }

    public void moveColumn() {
        column++;
    }

    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Token)) {
            return false;
        }
        Token other = (Token) obj;
        return (this.getColumn() == other.getColumn() &&
                this.getLine() == other.getLine() &&
                this.getContent().equals(other.getContent()) &&
                this.getType() == other.getType());
    }

    @Override
    public int hashCode() {
        int result = line;
        result = 31 * result + column;
        result = 31 * result + length;
        result = 31 * result + content.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("%3d:%-3d%20s    %s",
                this.line,
                this.column,
                this.type.toString(),
                (this.content.equals("\n") ? "<EOL>" : this.content)
        );
    }
}
