/*
* Copyright (c) Stuart Clark
*
* This project by Stuart Clark is free software: you can redistribute it and/or modify it under the terms
* of the GNU General Public License as published by the Free Software Foundation, either version 3 of the
* License, or (at your option) any later version. This project is distributed in the hope that it will be
* useful for educational purposes, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
* or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License along with this project.
* If not, please see the GNU website.
*/
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
