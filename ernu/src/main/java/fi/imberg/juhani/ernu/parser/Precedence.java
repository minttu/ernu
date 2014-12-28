package fi.imberg.juhani.ernu.parser;

/**
 * Describes the different infix parser precedences.
 */
public class Precedence {
    public static final int ASSIGNMENT = 1;
    public static final int OR = 2;
    public static final int AND = 3;
    public static final int EQUALITY = 4;
    public static final int COMPARISON = 5;
    public static final int SUM = 6;
    public static final int PRODUCT = 7;
    public static final int PREFIX = 8;
    public static final int CALL = 10;
}
