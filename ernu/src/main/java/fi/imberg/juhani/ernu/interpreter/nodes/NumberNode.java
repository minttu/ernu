package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.interfaces.Mathable;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Number nodes represent an infinite precision rational number.
 */
public class NumberNode implements Node, Comparable<NumberNode>, Mathable<NumberNode> {
    private BigInteger numerator;
    private BigInteger denominator;

    public NumberNode(int value) {
        this("" + value);
    }

    public NumberNode(String value) {
        if (value.contains(".")) {
            int len = value.length() - value.indexOf('.');
            String den = "1";
            String num = value.replace(".", "");
            for (int i = 1; i < len; i++) {
                den += "0";
            }
            this.numerator = new BigInteger(num);
            this.denominator = new BigInteger(den);
        } else {
            this.numerator = new BigInteger(value);
            this.denominator = BigInteger.ONE;
        }
        reduce();
    }

    public NumberNode(BigInteger numerator, BigInteger denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        reduce();
    }

    private void reduce() {
        if (!numerator.equals(BigInteger.ZERO)) {
            BigInteger common = numerator.abs().gcd(denominator);
            numerator = numerator.divide(common);
            denominator = denominator.divide(common);
        }
    }

    public BigInteger getNumerator() {
        return numerator;
    }

    public BigInteger getDenominator() {
        return denominator;
    }

    public int getInteger() {
        return getBigInteger().intValue();
    }

    public BigInteger getBigInteger() {
        return this.numerator.divide(this.denominator);
    }

    public BigDecimal getBigDecimal() {
        return new BigDecimal(numerator).divide(new BigDecimal(denominator), 30, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public int compareTo(NumberNode other) {
        return getNumerator().multiply(other.getDenominator())
                .compareTo(other.getNumerator().multiply(getDenominator()));
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NumberNode)) {
            return false;
        }
        NumberNode otherNumber = (NumberNode) other;
        return (getNumerator().equals(otherNumber.getNumerator()) &&
                getDenominator().equals(otherNumber.getDenominator()));
    }

    @Override
    public String toString() {
        if (this.denominator.equals(BigInteger.ONE)) {
            return this.numerator.toString();
        } else if (this.numerator.equals(BigInteger.ZERO)) {
            return "0";
        }
        return this.getBigDecimal().toString().replaceAll("0*$", "");
    }

    @Override
    public NumberNode add(NumberNode other) {
        return new NumberNode(getNumerator().multiply(other.getDenominator())
                .add(getDenominator().multiply(other.getNumerator())),
                getDenominator().multiply(other.getDenominator()));
    }

    @Override
    public NumberNode sub(NumberNode other) {
        return new NumberNode(getNumerator().multiply(other.getDenominator()).subtract(getDenominator().multiply(other.getNumerator())), getDenominator().multiply(other.getDenominator()));
    }

    @Override
    public NumberNode mul(NumberNode other) {
        return new NumberNode(getNumerator().multiply(other.getNumerator()), getDenominator().multiply(other.getDenominator()));
    }

    @Override
    public NumberNode div(NumberNode other) {
        return new NumberNode(getNumerator().multiply(other.getDenominator()), getDenominator().multiply(other.getNumerator()));
    }

    @Override
    public NumberNode mod(NumberNode other) {
        return new NumberNode(getNumerator().multiply(other.getDenominator()).remainder(getDenominator().multiply(other.getNumerator())), getDenominator().multiply(other.getDenominator()));
    }

    @Override
    public Node getValue(Environment environment) {
        return this;
    }
}
