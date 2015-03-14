package com.stuart.tourny.model.utils.dataFixture;

import com.stuart.tourny.model.common.dto.DTOTournament;
import com.stuart.tourny.model.engines.TournamentDbEngine;

import java.sql.Connection;
import java.sql.SQLException;

public class TournamentFixture {

  private TournamentDbEngine tournamentEngine;

  public TournamentFixture() {
    tournamentEngine = new TournamentDbEngine();
  }

  public DTOTournament addATournament(Connection conn,
                                      String tournyName,
                                      String tournyWinner,
                                      String spoon,
                                      String goldenBoot,
                                      long goals) throws SQLException {
    DTOTournament dto = new DTOTournament();
    dto.setTournamentName(tournyName);
    dto.setTournamentWinner(tournyWinner);
    dto.setWoodenSpoon(spoon);
    dto.setGoldenBoot(goldenBoot);
    dto.setGoldenBootGoals(goals);
    return tournamentEngine.addRecord(conn, dto);
  }

}
