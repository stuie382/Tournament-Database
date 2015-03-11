package com.stuart.tourny.model.engines;

import com.stuart.tourny.model.common.key.KeyGame;
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

public class GameDbEngineTest {

  private static Connection connTDB;
  private GameDbEngine uut;

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
    uut = new GameDbEngine();
  }

  @After
  public void tearDown() throws Exception {
    connTDB.rollback();
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testGetGame() throws Exception {
    KeyGame key = new KeyGame(99999);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Could not find Game record for KeyGame{gameId=99999}");
    uut.getGame(connTDB, key);
  }

  @Test
  public void testAddGameAndReturn() throws Exception {

  }

  @Test
  public void testAddGame() throws Exception {

  }

  @Test
  public void testUpdateGame() throws Exception {

  }

  @Test
  public void testDeleteGame() throws Exception {

  }
}
