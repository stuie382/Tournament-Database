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
