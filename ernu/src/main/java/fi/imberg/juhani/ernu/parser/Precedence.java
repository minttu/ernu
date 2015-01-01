package fi.imberg.juhani.ernu.parser;

/**
 * Describes the different infix parser precedences.
 */
public class Precedence {
    /**
     * {@code =}
     */
    public static final int ASSIGNMENT = 1;
    /**
     * {@code || or}
     */
    public static final int OR = 2;
    /**
     * {@code && and}
     */
    public static final int AND = 3;
    /**
     * {@code == !=}
     */
    public static final int EQUALITY = 4;
    /**
     * {@code >= <= < >}
     */
    public static final int COMPARISON = 5;
    /**
     * {@code + -}
     */
    public static final int SUM = 6;
    /**
     * {@code * /}
     */
    public static final int PRODUCT = 7;
    /**
     * {@code ! -}
     */
    public static final int PREFIX = 8;
    /**
     * {@code a() a[]}
     */
    public static final int CALL = 10;
}
