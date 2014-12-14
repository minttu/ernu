package fi.imberg.juhani.ernu.lang.node;

public class StringNode implements Node {
    private final String stringLiteral;

    public StringNode(String stringLiteral) {
        this.stringLiteral = stringLiteral.substring(1, stringLiteral.length() - 1);
    }

    public String getStringLiteral() {
        return stringLiteral;
    }

    @Override
    public String toString() {
        return "\"" + stringLiteral + "\"";
    }
}
