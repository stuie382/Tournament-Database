package com.stuart.tourny.model.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import base.TestBase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ConnectionManagerTest extends TestBase {

  private Connection conn;

  /* This class does not contain the before/after class methods
  as it only contains a single test. The test is simply to connect
  to the database and see if we can select from it.
   */

  @Before
  public void setUp() throws Exception {
    conn = ConnectionManager.getInstance().getConnection();
    conn.setSavepoint();
  }

  @After
  public void tearDown() throws Exception {
    conn.rollback();
    conn.close();
  }

  @Test
  public void testInsertTest() throws Exception {
    String insert = "SELECT 1 FROM SYSIBM.SYSDUMMY1";
    try (PreparedStatement ps = conn.prepareStatement(insert)) {
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          long result = rs.getLong(1);
          assertEquals(1, result);
        }
      }
    } catch (Exception ex) {
      fail("insert test fail: " + ex.getMessage());
    }
  }
}
