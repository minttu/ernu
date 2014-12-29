package fi.imberg.juhani.ernu.util;

/**
 * Describes a range between two integers.
 */
public class Range {
    private int start;
    private int end;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public Range(int start) {
        this(start, -1);
    }

    public Range() {
        this(0, -1);
    }

    public boolean inRange(int value) {
        if (end == -1) {
            return start <= value;
        }
        return (start <= value && value <= end);
    }

    @Override
    public String toString() {
        return start + " to " + (end == -1 ? "n" : end);
    }
}
