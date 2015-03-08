package com.stuart.tourny.model.utils;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.Assert.assertEquals;

public class ConnectionManagerTest {

  private Connection conn;

  @org.junit.Before
  public void setUp() throws Exception {
    conn = ConnectionManager.getInstance().getConnection();
  }

  @org.junit.After
  public void tearDown() throws Exception {
    conn.close();
  }

  @Test
  public void testInsertTest() throws Exception {
    System.out.println(conn.getSchema());
    String insert = "INSERT INTO tdb.player VALUES ('bob')";
    int updated = -1;
    try (PreparedStatement s = conn.prepareStatement(insert)) {
      updated = s.executeUpdate();
    } catch (Exception ex) {
      System.out.println("insert test fail: " + ex.getMessage());
    }
    assertEquals(1, updated);
  }
}
