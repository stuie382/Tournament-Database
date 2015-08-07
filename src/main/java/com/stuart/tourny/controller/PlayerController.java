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

import com.stuart.tourny.model.common.dto.DTOPlayer;
import com.stuart.tourny.model.engines.PlayerDbEngine;
import com.stuart.tourny.model.utils.ConnectionManager;
import com.stuart.tourny.model.utils.exceptions.ServerProblem;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;

/**
 * <p>Controller class that will manage the {@link Connection} objects used to query against the
 * PLAYER table.</p><p> This should handle any commits required and should log at DEBUG/ERROR levels
 * when a transaction completes/exception occurs.</p>
 */
public class PlayerController {

  private static final Logger log = Logger.getLogger(PlayerController.class);

  private final PlayerDbEngine engine;

  public PlayerController() {
    engine = new PlayerDbEngine();
  }

  /**
   * Add a new player record to the database.
   *
   * @param dto
   *     - DTO representing the new Player to add
   *
   * @return - DTOPlayer of the newly added record.
   *
   * @throws ServerProblem
   */
  public DTOPlayer addPlayer(DTOPlayer dto) throws ServerProblem {
    try (Connection connTDB = ConnectionManager.getInstance()
        .getConnection()) {
      dto = engine.addPlayer(connTDB, dto);
      connTDB.commit();
      log.debug("Added new Player: " + dto);
      return dto;
    } catch (Exception ex) {
      String error = "Problem encountered adding a player: " + ex;
      log.error(error);
      throw new ServerProblem(error);
    }
  }

  /**
   * Get all players from the database.
   *
   * @return - List of all players
   *
   * @throws ServerProblem
   */
  public List<String> getAllPlayers() throws ServerProblem {
    try (Connection connTDB = ConnectionManager.getInstance()
        .getConnection()) {
      log.debug("Getting all players");
      List<String> results = engine.getAllPlayers(connTDB);
      log.debug("Players found in the database: " + results);
      return results;
    } catch (Exception ex) {
      String error = "Problem encountered getting all players: " + ex;
      log.error(error);
      throw new ServerProblem(error);
    }
  }


}
