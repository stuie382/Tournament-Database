package com.stuart.tourny.model.engines;

import com.stuart.tourny.model.common.dto.DTOPlayer;
import com.stuart.tourny.model.common.key.KeyPlayer;
import com.stuart.tourny.model.utils.ConnectionManager;
import com.stuart.tourny.model.utils.dataFixture.PlayerFixture;

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
    thrown.expectMessage("Cannot find DTOPlayer! KeyPlayer{playerId='Stuart'}");
    uut.getPlayer(connTDB, key);
  }
}
