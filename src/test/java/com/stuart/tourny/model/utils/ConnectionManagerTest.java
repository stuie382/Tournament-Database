package com.stuart.tourny.model.utils;

import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class ConnectionManagerTest {

  private Connection conn;

  @org.junit.Before
  public void setUp() throws Exception {
    conn = ConnectionManager.getInstance().getConnection(Constants.DBS_TDB);
  }

  @org.junit.After
  public void tearDown() throws Exception {
    conn.close();
  }

  @Test
  public void testInsertTest() throws Exception {
    System.out.println(conn.getSchema());
    conn.setSchema(Constants.DBS_TDB);
    String insert = "INSERT INTO player VALUES ('bob')";
    int updated = -1;
    try {
      Statement s = conn.createStatement();
      updated = s.executeUpdate(insert);
    } catch (Exception ex) {
      System.out.println("insert test fail: " + ex.getMessage());
    }
    assertEquals(1, updated);
  }
}
