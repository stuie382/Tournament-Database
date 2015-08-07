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
