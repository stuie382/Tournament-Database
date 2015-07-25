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

import com.stuart.tourny.model.common.dto.DTOGame;
import com.stuart.tourny.model.common.key.KeyGame;
import com.stuart.tourny.model.engines.GameDbEngine;
import com.stuart.tourny.model.utils.ConnectionManager;
import com.stuart.tourny.model.utils.exceptions.ServerProblem;

import org.apache.log4j.Logger;

import java.sql.Connection;

/**
 * <p>Controller class that will manage the {@link Connection} objects used to query against the
 * GAME table.</p><p> This should handle any commits required and should log at DEBUG/ERROR levels
 * when a transaction completes/exception occurs.</p>
 */
public class GameController {

  private static final Logger log = Logger.getLogger(GameController.class);

  private GameDbEngine engine;

  public GameController() {
    engine = new GameDbEngine();
  }

  /**
   * Add a new Game record to the database. This will commit the connection if the add is
   * successful.
   *
   * @param dto
   *     Record to add to the database
   */
  public void addGame(DTOGame dto) throws ServerProblem {
    try (Connection connTDB = ConnectionManager.getInstance()
        .getConnection()) {
      log.debug("Attempting to add new GAME: " + dto.toString());
      engine.addGame(connTDB, dto);
      connTDB.commit();
    } catch (Exception ex) {
      String error = "Problem encountered during addGame: " + ex;
      log.error(error);
      throw new ServerProblem(error);
    }
  }

  /**
   * Get an existing Game record from the database.
   *
   * @param key
   *     KeyGame record of the record to fetch
   *
   * @return DTOGame record from the database
   */
  public DTOGame getGame(KeyGame key) throws ServerProblem {
    try (Connection connTDB = ConnectionManager.getInstance()
        .getConnection()) {
      log.debug("Getting game from database: " + key);
      return engine.getGame(connTDB, key);
    } catch (Exception ex) {
      String error = "Problem encountered during getGame: " + ex;
      log.error(error);
      throw new ServerProblem(error);
    }
  }

  /**
   * Update an existing record with new values.
   *
   * @param dto
   *     Record containing updated information
   *
   * @return Record with updated information from the database
   */
  public DTOGame updateGame(DTOGame dto) throws ServerProblem {
    try (Connection connTDB = ConnectionManager.getInstance()
        .getConnection()) {
      log.debug("Attempting to update record: " + dto);
      dto = engine.updateGame(connTDB, dto);
      connTDB.commit();
      return dto;
    } catch (Exception ex) {
      String error = "Problem encountered during updateGame: " + ex;
      log.error(error);
      throw new ServerProblem(error);
    }
  }

  /**
   * Delete the row from the database.
   *
   * @param dto
   *     Row to delete
   */
  public void deleteGame(DTOGame dto) throws ServerProblem {
    try (Connection connTDB = ConnectionManager.getInstance()
        .getConnection()) {
      log.debug("Attempting to delete game: " + dto);
      engine.deleteGame(connTDB, dto);
      connTDB.commit();
    } catch (Exception ex) {
      String error = "Problem encountered during deleteGame: " + ex;
      log.error(error);
      throw new ServerProblem(error);
    }
  }

}
