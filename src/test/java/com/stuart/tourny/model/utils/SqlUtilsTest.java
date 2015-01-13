package com.stuart.tourny.model.utils;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SqlUtilsTest extends TestCase {

    @Before
    public void setUp () throws Exception {
    }

    @After
    public void tearDown () throws Exception {
    }

    @Test
    public void testStb_Upper_Y () throws Exception {
        String input = "Y";
        boolean result = SqlUtils.stb (input);
        assertTrue (result);
    }

    @Test
    public void testStb_Lower_Y () throws Exception {
        String input = "y";
        boolean result = SqlUtils.stb (input);
        assertTrue (result);
    }

    @Test
    public void testStb_Word_1 () throws Exception {
        String input = "Yeah";
        boolean result = SqlUtils.stb (input);
        assertFalse (result);
    }

    @Test
    public void testStb_Word_2 () throws Exception {
        String input = "yeah";
        boolean result = SqlUtils.stb (input);
        assertFalse (result);
    }

    @Test
    public void testStb_Null () throws Exception {
        String input = null;
        boolean result = SqlUtils.stb (input);
        assertFalse (result);
    }

    @Test
    public void testBts_True () throws Exception {
        boolean input = true;
        String result = SqlUtils.bts (input);
        assertEquals ("Y", result);
    }

    @Test
    public void testBts_False () throws Exception {
        boolean input = false;
        String result = SqlUtils.bts (input);
        assertEquals ("N", result);
    }
}
