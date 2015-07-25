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
package com.stuart.tourny.model.engines;

import com.stuart.tourny.model.common.dto.DTOPlayer;
import com.stuart.tourny.model.common.key.KeyPlayer;
import com.stuart.tourny.model.utils.ConnectionManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import base.dataFixture.PlayerFixture;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class PlayerDbEngineTest {

  private static Connection connTDB;
  private PlayerDbEngine uut;
  private PlayerFixture playerFixture;

  @BeforeClass
  public static void beforeSetUp() throws SQLException, PropertyVetoException {
    connTDB = ConnectionManager.getInstance().getConnection();
    connTDB.setSavepoint();
  }

  @AfterClass
  public static void afterTearDown() throws SQLException {
    connTDB.close();
  }

  @Before
  public void setUp() throws Exception {
    uut = new PlayerDbEngine();
    playerFixture = new PlayerFixture();
  }

  @After
  public void tearDown() throws Exception {
    connTDB.rollback();
  }

  /**
   * Rule used to define the class of exception and the exception message we should expect from a
   * method.
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();


  /**
   * Simple test to add a single player record, return it, and validate it looks sensible.
   */
  @Test
  public void testAddPlayer()
      throws Exception {
    DTOPlayer dto = new DTOPlayer();
    dto.setName("Stuart");
    dto = uut.addPlayer(connTDB, dto);
    assertNotNull(dto);
    assertEquals("Stuart", dto.getName());
    assertEquals("PlayerEngine", dto.getCreatedByUserId());
    assertEquals("PlayerEngine", dto.getUpdatedByUserId());
    assertNotNull(dto.getCreateDatetime());
    assertNotNull(dto.getUpdateDatetime());
  }

  /**
   * Simple test to get a specific player record from the database.
   */
  @Test
  public void testGetAPlayer()
      throws Exception {
    playerFixture.addAPlayer(connTDB, "Stuart");
    KeyPlayer key = new KeyPlayer("Stuart");
    DTOPlayer dto = uut.getPlayer(connTDB, key);
    assertNotNull(dto);
    assertEquals("Stuart", dto.getName());
  }

  /**
   * Simple test to delete a player record from the database.
   *
   * @throws Exception
   */
  @Test
  public void testDeleteAPlayer()
      throws Exception {
    // Setup the data
    playerFixture.addAPlayer(connTDB, "Stuart");
    KeyPlayer key = new KeyPlayer("Stuart");
    DTOPlayer dto = uut.getPlayer(connTDB, key);
    assertNotNull(dto);
    assertEquals("Stuart", dto.getName());

    // Delete the data
    uut.deletePlayer(connTDB, dto);

    // Check the data has been removed by checking for the key
    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("Cannot find DTOPlayer! KeyPlayer{player=Stuart}");
    uut.getPlayer(connTDB, key);
  }

  /**
   * Simple test to show that the system will throw the expected error when the row hash in memory
   * and on disk are different.
   *
   * @throws Exception
   */
  @Test
  public void testDeleteAPlayer_RowHashProblem()
      throws Exception {
    // Setup the data
    playerFixture.addAPlayer(connTDB, "Stuart");
    KeyPlayer key = new KeyPlayer("Stuart");
    DTOPlayer dto = uut.getPlayer(connTDB, key);
    assertNotNull(dto);
    assertEquals("Stuart", dto.getName());

    dto.setRowHash("Broken");

    // This will now throw an exception as it thinks the in-memory record
    // and on disk record are different.
    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("Delete Player failed: record changed by another process");
    // Delete the data
    uut.deletePlayer(connTDB, dto);
  }
}
