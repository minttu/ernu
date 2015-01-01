package fi.imberg.juhani.ernu.util;

/**
 * Describes a range between two integers.
 */
public class Range {
    /**
     * From where this range starts
     */
    private int start;
    /**
     * To where this range ends, -1 if infinity
     */
    private int end;

    /**
     * @param start From where this range starts
     * @param end   To where this range ends, -1 if infinity
     */
    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Ends at infinity
     *
     * @param start From where this range starts
     */
    public Range(int start) {
        this(start, -1);
    }

    /**
     * An empty range
     */
    public Range() {
        this(0, -1);
    }

    /**
     * Is the given value in the range
     *
     * @param value the integer to check against
     * @return True if the value is in the range
     */
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
