package fi.imberg.juhani.ernu.parser;

import java.util.ArrayDeque;

/**
 * Splits a source string to tokens.
 */
public class Tokenizer {
    /**
     * The sourcecode the tokenizer is working on currently
     */
    private char[] source;

    /**
     * The current line number
     */
    private int line;
    /**
     * The current column number
     */
    private int column;

    /**
     * Are we currently inside a string?
     */
    private boolean inString;

    /**
     * The tokens we have already tokenized from the text
     */
    private ArrayDeque<Token> tokens;

    public Tokenizer() {
        this.source = "".toCharArray();
        this.tokens = new ArrayDeque<>();
    }

    private void createNew(String initial) {
        if (!isEmpty() && inString) {
            this.tokens.getLast().add(initial);
            return;
        }
        if (!tokens.isEmpty() && tokens.getLast().isEmpty()) {
            tokens.removeLast();
        }
        this.tokens.addLast(new Token(this.line, this.column, initial));
    }

    private void addToLast(char c) {
        if (tokens.isEmpty()) {
            createNew();
        }
        this.tokens.getLast().add(c);
    }

    private void createNew() {
        createNew("");
    }

    private void createNew(char c) {
        createNew("" + c);
    }

    /**
     * Creates tokens out of the text
     *
     * @param text The string to be tokenized.
     */
    public void tokenize(String text) {
        source = text.toCharArray();
        line = 0;
        column = 0;
        inString = false;
        boolean skipLine = false;
        for (char c : source) {
            if (skipLine && c != '\n') {
                column++;
                continue;
            }
            if (inString && c != '\"') {
                addToLast(c);
                column++;
                continue;
            }
            switch (c) {
                case '#':
                    skipLine = true;
                    break;
                case '\"':
                    createNew("\"");
                    inString = !inString;
                    break;
                case ' ':
                    createNew();
                    break;
                case '\n':
                    skipLine = false;
                    createNew("\n");
                    line++;
                    column = 0;
                    continue;
                case '&':
                case '|':
                    if (!isEmpty()) {
                        if (this.tokens.getLast().getLength() == 1) {
                            String last = this.tokens.getLast().getContent();
                            if (last.charAt(0) == c) {
                                addToLast(c);
                                break;
                            }
                        }
                    }
                    createNew(c);
                    break;
                case '=':
                    if (!isEmpty()) {
                        if (this.tokens.getLast().getLength() == 1) {
                            String last = this.tokens.getLast().getContent();
                            if (last.matches("[\\+-/\\*%=><!]")) {
                                addToLast(c);
                                break;
                            }
                        }
                    }
                case '+':
                case '-':
                case '/':
                case '*':
                case '%':
                case '>':
                case '<':
                case '!':
                case '(':
                case ')':
                case '[':
                case ']':
                case ',':
                case ':':
                case ';':
                    createNew(c);
                    break;
                case '.':
                    if (!isEmpty()) {
                        Token token = tokens.getLast();
                        String str = token.getContent();
                        if (str.matches(TokenType.NUMBER.getMatch())) {
                            addToLast(c);
                            break;
                        }
                    }
                    createNew(c);
                    break;
                default:
                    if (!isEmpty()) {
                        Token token = tokens.getLast();
                        if (token.getContent().matches(TokenType.IDENTIFIER.getMatch()) ||
                                token.getContent().matches(TokenType.NUMBER.getMatch()) ||
                                (token.getContent().equals("-") && c >= 48 && c <= 57)) {
                            addToLast(c);
                        } else {
                            createNew(c);
                        }
                        break;
                    }
                    addToLast(c);
                    break;
            }

            column++;
        }
    }

    /**
     * @return The next token
     */
    public Token nextToken() {
        Token token = this.tokens.removeFirst();
        TokenType.matchType(token);
        return token;
    }

    public boolean isEmpty() {
        return this.tokens.isEmpty();
    }

}
