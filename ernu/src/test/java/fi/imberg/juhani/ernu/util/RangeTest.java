package fi.imberg.juhani.ernu.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RangeTest {
    Range range;
    @Before
    public void init() {
        range = new Range(0, 10);
    }
    @After
    public void destroy() {
        range = null;
    }
    @Test
    public void testToString() {
        Assert.assertEquals("0 to 10", range.toString());
        range = new Range(4);
        Assert.assertEquals("4 to n", range.toString());
        range = new Range();
        Assert.assertEquals("0 to n", range.toString());
    }
    @Test
    public void testInRange() {
        Assert.assertTrue(range.inRange(0));
        Assert.assertTrue(range.inRange(10));
        range = new Range(4);
        Assert.assertFalse(range.inRange(3));
        Assert.assertTrue(range.inRange(190));
    }
}
