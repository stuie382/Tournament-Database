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

    }

    /*
        public Test () {
            try {
                init ();
                System.out.println (tester (ConnectionManager.getInstance ().getConnection ()));
                System.out.println (insertTest (ConnectionManager.getInstance ().getConnection ()));
                System.out.println (getInsertTest (ConnectionManager.getInstance ().getConnection
                 ()));
            } catch (Exception ex) {
                System.out.println ("All gone wrong: " + ex.getMessage ());
            }
        }

        public List<String> tester (Connection con) throws SQLException {
            List<String> result = new ArrayList<> ();
            Statement s = con.createStatement ();
            ResultSet rs = s.executeQuery ("select * from app.PLAYER");
            while (rs.next ()) {
                result.add (rs.getString (1));
            }
            con.close ();
            return result;
        }
    */
    @Test
    public void testInsertTest () throws Exception {

        String insert = "insert into player values ('bob')";
        int updated = -1;
        try {
            Statement s = conn.createStatement ();
            updated = s.executeUpdate (insert);
            conn.close ();
        } catch (Exception ex) {
            System.out.println ("insert test fail: " + ex.getMessage ());
        }
        assertEquals (1, updated);
    }
/*
    public String getInsertTest (Connection con) {
        String sql = "select * from app.player where name = 'bob'";
        String result = "";
        try {
            Statement s = con.createStatement ();
            ResultSet rs = s.executeQuery (sql);
            if (rs.next ()) {
                result = rs.getString (1);
            }
            con.close ();
        } catch (Exception ex) {
            System.out.println ("getInsertTest fail: " + ex.getMessage ());
        }
        return result;
    }*/
}
