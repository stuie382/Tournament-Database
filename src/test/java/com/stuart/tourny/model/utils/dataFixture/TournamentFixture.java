package com.stuart.tourny.model.utils.dataFixture;

import com.stuart.tourny.model.common.dto.DTOTournament;
import com.stuart.tourny.model.engines.TournamentDbEngine;

import java.sql.Connection;

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
                                      long goals) {

    DTOTournament dto = new DTOTournament();
    //TODO this
    return dto;
  }

}
