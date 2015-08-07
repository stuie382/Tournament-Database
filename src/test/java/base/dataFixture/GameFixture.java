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
package base.dataFixture;

import com.stuart.tourny.model.common.dto.DTOGame;
import com.stuart.tourny.model.engines.GameDbEngine;

import java.sql.Connection;
import java.sql.SQLException;

public class GameFixture {

  private GameDbEngine gameEngine;

  public GameFixture() {
    this.gameEngine = new GameDbEngine();
  }

  /**
   * Create a simple game, with player 1 the winner in normal time.
   *
   * @return - A fully populated DTOGame
   *
   * @throws SQLException
   */
  public DTOGame createGame(Connection connTDB, String player1, String player2, long tournamentId)
      throws SQLException {
    DTOGame game = new DTOGame();
    game.setHomePlayer(player1);
    game.setAwayPlayer(player2);
    game.setTournamentId(tournamentId);
    game.setHomeGoals(2);
    game.setAwayGoals(1);
    game.setExtraTime("N");
    game.setHomePens(0);
    game.setAwayPens(0);
    game.setWinner(player1);
    game.setKnockOut("N");
    game = gameEngine.addGame(connTDB, game);
    return game;
  }
}
