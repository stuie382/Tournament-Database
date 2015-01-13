package com.stuart.tourny.model.utils;

import junit.framework.TestCase;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;

public class ConnectionManagerTest extends TestCase {

    private Connection conn;

    @org.junit.Before
    public void setUp () throws Exception {
        conn = ConnectionManager.getInstance ().getConnection ();
    }

    @org.junit.After
    public void tearDown () throws Exception {
        conn.close ();
    }

    @Test
    public void testInsertTest () throws Exception {

        String insert = "INSERT INTO player VALUES ('bob')";
        int updated = -1;
        try {
            Statement s = conn.createStatement ();
            updated = s.executeUpdate (insert);
        } catch (Exception ex) {
            System.out.println ("insert test fail: " + ex.getMessage ());
        }
        assertEquals (1, updated);
    }
}
