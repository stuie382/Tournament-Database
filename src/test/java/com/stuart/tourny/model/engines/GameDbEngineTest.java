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

import com.stuart.tourny.model.common.dto.DTOGame;
import com.stuart.tourny.model.common.dto.DTOTournament;
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

import base.TestBase;
import base.dataFixture.GameFixture;
import base.dataFixture.PlayerFixture;
import base.dataFixture.TournamentFixture;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;


public class GameDbEngineTest extends TestBase {

  private static Connection connTDB;
  private GameDbEngine uut;

  private static PlayerFixture playerFixture;
  private static TournamentFixture tournamentFixture;
  private static GameFixture gameFixture;
  private static DTOTournament dtoTournament;

  private static final String PLAYER_1 = "player1";
  private static final String PLAYER_2 = "player2";
  private static final String TOURNAMENT = "Jan 2015";

  @BeforeClass
  public static void beforeSetUp() throws SQLException, PropertyVetoException {
    connTDB = ConnectionManager.getInstance().getConnection();
    connTDB.setSavepoint();
    playerFixture = new PlayerFixture();
    tournamentFixture = new TournamentFixture();
    gameFixture = new GameFixture();
  }

  @AfterClass
  public static void afterTearDown() throws SQLException {
    connTDB.close();
  }

  @Before
  public void setUp() throws Exception {
    uut = new GameDbEngine();
    playerFixture.addAPlayer(connTDB, PLAYER_1);
    playerFixture.addAPlayer(connTDB, PLAYER_2);
    dtoTournament = tournamentFixture.addATournament(connTDB, TOURNAMENT, null, null, null, -1);
  }

  @After
  public void tearDown() throws Exception {
    connTDB.rollback();
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Try and get a game that does not exist.
   *
   * @throws Exception
   */
  @Test
  public void testGetGame_DoesNotExist() throws Exception {
    KeyGame key = new KeyGame(99999);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Could not find Game record for KeyGame{gameId=99999}");
    uut.getGame(connTDB, key);
  }

  /**
   * Create a populated game and assert that all the fields are correctly populated.
   *
   * @throws Exception
   */
  @Test
  public void testAddGame() throws Exception {
    DTOGame game = gameFixture.createGame(connTDB, PLAYER_1, PLAYER_2,
                                          dtoTournament.getTournamentId());

    assertNotNull(game);
    assertEquals(PLAYER_1, game.getHomePlayer());
    assertEquals(PLAYER_2, game.getAwayPlayer());
    assertEquals(dtoTournament.getTournamentId(), game.getTournamentId());
    assertEquals(2, game.getHomeGoals());
    assertEquals(1, game.getAwayGoals());
    assertEquals(false, game.getExtraTime());
    assertEquals(0, game.getHomePens());
    assertEquals(0, game.getAwayPens());
    assertEquals(PLAYER_1, game.getWinner());
    assertEquals(false, game.getKnockOut());
    assertEquals("GameEngine", game.getCreatedUser());
    assertEquals("GameEngine", game.getUpdatedUser());

  }

  /**
   * Take the simple game, and make it a draw, with play two winning after penalties.
   *
   * @throws Exception
   */
  @Test
  public void testUpdateGame() throws Exception {
    DTOGame game = gameFixture.createGame(connTDB, PLAYER_1, PLAYER_2,
                                          dtoTournament.getTournamentId());
    game.setExtraTime("Y");
    game.setAwayGoals(2);
    game.setHomePens(4);
    game.setAwayPens(5);
    game.setWinner(PLAYER_2);

    DTOGame updated = uut.updateGame(connTDB, game);
    assertEquals(true, updated.getExtraTime());
    assertEquals(2, updated.getAwayGoals());
    assertEquals(4, updated.getHomePens());
    assertEquals(5, updated.getAwayPens());
    assertEquals(PLAYER_2, updated.getWinner());

  }

  /**
   * Similar test to {@code testUpdateGame} but we will manually corrupt the row hash before trying
   * to update the record.
   *
   * @throws Exception
   */
  @Test
  public void testUpdateGame_BrokenRowHash() throws Exception {
    DTOGame game = gameFixture.createGame(connTDB, PLAYER_1, PLAYER_2,
                                          dtoTournament.getTournamentId());
    game.setExtraTime("Y");
    game.setAwayGoals(2);
    game.setHomePens(4);
    game.setAwayPens(5);
    game.setWinner(PLAYER_2);
    game.setRowHash("Broken");
    long gameId = game.getGameId();

    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("Game record out of date: KeyGame{" + "gameId=" + gameId + "}");
    uut.updateGame(connTDB, game);
  }

  /**
   * Add a game then try to delete it.
   *
   * @throws Exception
   */
  @Test
  public void testDeleteGame() throws Exception {
    DTOGame game = gameFixture.createGame(connTDB, PLAYER_1, PLAYER_2,
                                          dtoTournament.getTournamentId());
    uut.deleteGame(connTDB, game);
  }

  /**
   * Try and delete a game that does not exist.
   *
   * @throws Exception
   */
  @Test
  public void testDeleteGame_DoesNotExist() throws Exception {
    DTOGame game = new DTOGame();
    game.setGameId(999999);

    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Could not find Game record for KeyGame{gameId=999999}");
    uut.deleteGame(connTDB, game);
  }

  /**
   * Try and delete a game after corrupting the row hash.
   *
   * @throws Exception
   */
  @Test
  public void testDeleteGame_BrokenRowHash() throws Exception {
    DTOGame game = gameFixture.createGame(connTDB, PLAYER_1, PLAYER_2,
                                          dtoTournament.getTournamentId());
    game.setRowHash("Broken");

    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("delete Game: record changed by another process");
    uut.deleteGame(connTDB, game);
  }
}
