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
package com.stuart.tourny.controller;

import com.stuart.tourny.model.common.dto.DTOTournament;
import com.stuart.tourny.model.engines.TournamentDbEngine;
import com.stuart.tourny.model.utils.ConnectionManager;
import com.stuart.tourny.model.utils.exceptions.ServerProblem;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;

/**
 * <p>Controller class that will manage the {@link Connection} objects used to query against the
 * TOURNAMENT table.</p><p> This should handle any commits required and should log at DEBUG/ERROR
 * levels when a transaction completes/exception occurs.</p>
 */
public class TournamentController {

  private static final Logger log = Logger.getLogger(TournamentController.class);

  private final TournamentDbEngine engine;

  public TournamentController() {
    engine = new TournamentDbEngine();
  }

  public DTOTournament addTournament(DTOTournament dto) throws ServerProblem {
    try (Connection connTDB = ConnectionManager.getInstance()
        .getConnection()) {
      dto = engine.addRecord(connTDB, dto);
      connTDB.commit();
      log.debug("Added new Tournament: " + dto);
      return dto;
    } catch (Exception ex) {
      String error = "Problem encountered adding a tournament: " + ex;
      log.error(error);
      throw new ServerProblem(error);
    }
  }

  /**
   * Get all the tournament names from the database.
   *
   * @return - List of all tournament names
   *
   * @throws ServerProblem
   */
  public List<String> getTournaments() throws ServerProblem {
    try (Connection connTDB = ConnectionManager.getInstance()
        .getConnection()) {
      log.debug("Attempting to get all the tournament names");
      return engine.getTournaments(connTDB);
    } catch (Exception ex) {
      log.error(ex);
      throw new ServerProblem("Problem getting tournaments: " + ex);
    }
  }

  /**
   * Get the tournament ID for the given tournament name.
   *
   * @param tournamentName
   *     - The name fo the tournament we want the ID for
   *
   * @return - Long representing the unique ID of the tournament
   *
   * @throws ServerProblem
   */
  public DTOTournament getDTOTournamentFromName(final String tournamentName) throws ServerProblem {
    try (Connection connTDB = ConnectionManager.getInstance()
        .getConnection()) {
      log.debug("Attempting to get the tournament ID from the name");
      return engine.getDTOTournamentFromName(connTDB, tournamentName);
    } catch (Exception ex) {
      log.error(ex);
      throw new ServerProblem("Problem getting tournament ID: " + ex);
    }
  }

}
