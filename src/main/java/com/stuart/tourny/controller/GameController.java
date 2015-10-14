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

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.stuart.tourny.model.common.dto.DTOGame;
import com.stuart.tourny.model.common.key.KeyGame;
import com.stuart.tourny.model.engines.GameDbEngine;
import com.stuart.tourny.model.utils.ConnectionManager;
import com.stuart.tourny.model.utils.exceptions.ServerProblem;

/**
 * <p>
 * Controller class that will manage the {@link Connection} objects used to
 * query against the GAME table.
 * </p>
 * <p>
 * This should handle any commits required and should log at DEBUG/ERROR levels
 * when a transaction completes/exception occurs.
 * </p>
 */
public class GameController {

    private static final Logger LOGGER = Logger.getLogger(GameController.class);

    private GameDbEngine engine;

    public GameController() {
	engine = new GameDbEngine();
    }

    /**
     * Add a new Game record to the database. This will commit the connection if
     * the add is successful.
     *
     * @param dto
     *            Record to add to the database
     */
    public DTOGame addGame(DTOGame dto) throws ServerProblem {
	try (Connection connTDB = ConnectionManager.getInstance().getConnection()) {
	    LOGGER.debug("Attempting to add new GAME: " + dto.toString());
	    DTOGame newGame = engine.addGame(connTDB, dto);
	    connTDB.commit();
	    LOGGER.debug("Game added:" + newGame);
	    return newGame;
	} catch (Exception ex) {
	    String error = "Problem encountered during addGame: " + ex;
	    LOGGER.error(error);
	    throw new ServerProblem(error, ex);
	}
    }

    /**
     * Get an existing Game record from the database.
     *
     * @param key
     *            KeyGame record of the record to fetch
     *
     * @return DTOGame record from the database
     */
    public DTOGame getGame(KeyGame key) throws ServerProblem {
	try (Connection connTDB = ConnectionManager.getInstance().getConnection()) {
	    LOGGER.debug("Getting game from database: " + key);
	    return engine.getGame(connTDB, key);
	} catch (Exception ex) {
	    String error = "Problem encountered during getGame: " + ex;
	    LOGGER.error(error);
	    throw new ServerProblem(error, ex);
	}
    }

    /**
     * Update an existing record with new values.
     *
     * @param dto
     *            Record containing updated information
     *
     * @return Record with updated information from the database
     */
    public DTOGame updateGame(DTOGame dto) throws ServerProblem {
	try (Connection connTDB = ConnectionManager.getInstance().getConnection()) {
	    LOGGER.debug("Attempting to update record: " + dto);
	    DTOGame newDto = engine.updateGame(connTDB, dto);
	    connTDB.commit();
	    return newDto;
	} catch (Exception ex) {
	    String error = "Problem encountered during updateGame: " + ex;
	    LOGGER.error(error);
	    throw new ServerProblem(error, ex);
	}
    }

    /**
     * Delete the row from the database.
     *
     * @param dto
     *            Row to delete
     */
    public void deleteGame(DTOGame dto) throws ServerProblem {
	try (Connection connTDB = ConnectionManager.getInstance().getConnection()) {
	    LOGGER.debug("Attempting to delete game: " + dto);
	    engine.deleteGame(connTDB, dto);
	    connTDB.commit();
	} catch (Exception ex) {
	    String error = "Problem encountered during deleteGame: " + ex;
	    LOGGER.error(error);
	    throw new ServerProblem(error, ex);
	}
    }

    /**
     * Get all the teams from the database for the users to select.
     *
     * @return - List of all teams
     *
     * @throws ServerProblem
     */
    public List<String> getTeams() throws ServerProblem {
	try (Connection connTDB = ConnectionManager.getInstance().getConnection()) {
	    LOGGER.debug("Attempting to get all the teams");
	    return engine.getTeams(connTDB);
	} catch (Exception ex) {
	    String error = "Problem encountered during getTeams: " + ex;
	    LOGGER.error(error);
	    throw new ServerProblem(error, ex);
	}
    }

}
