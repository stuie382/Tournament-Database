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
import java.util.Map;

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

  public DTOTournament updateTournament(final DTOTournament dto ) throws ServerProblem {
    try (Connection connTDB = ConnectionManager.getInstance().getConnection()) {
      log.debug("Attempting to update this tournament: " + dto);
      DTOTournament updatedDto = engine.updateRecord(connTDB,dto);
      connTDB.commit();
      return updatedDto;
    } catch (Exception ex) {
      log.error("Problem updating tournament: " + ex.getMessage());
      throw new ServerProblem("Problem updating tournament: ", ex);
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

  /**
   * For a given tournament ID, find out the number of goals scored in a tournament (excluding
   * penalty shoot out goals) by each player. The results will be sorted in descending order
   * (highest goals scored first). Then find out which player/s scored the most and return a new map
   * of the golden goal amount and the player/s who scored that amount.
   *
   * @param tournamentId
   *     - The tournament we want to know the golden goal winner for
   *
   * @return - Map of the golden goal amount and the player/s who won
   *
   * @throws ServerProblem
   */
  public Map<Long, String> calculateGoldenBootForTournament(final long tournamentId)
      throws ServerProblem {
    try (Connection connTDB = ConnectionManager.getInstance().getConnection()) {
      log.debug("Attempting to calculate the golden boot winner");
      Map<Long, String> results = engine.calculateGoldenBootForTournament(connTDB, tournamentId);
      log.debug("Golden boot winner: " + results);
      return results;
    } catch (Exception ex) {
      log.error("Problem calculating golden boot: " + ex.getMessage());
      throw new ServerProblem("Problem getting golden boot: " + ex);
    }
  }

}
