package com.stuart.tourny.controller;

import com.stuart.tourny.model.common.dto.DTOGame;
import com.stuart.tourny.model.common.key.KeyGame;
import com.stuart.tourny.model.engines.GameDbEngine;
import com.stuart.tourny.model.utils.ConnectionManager;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class GameController {

  private GameDbEngine engine;

  public GameController() {
    engine = new GameDbEngine();
  }

  /**
   * Add a new Game record to the database. This will commit the connection if the add is
   * successful.
   *
   * @param dto Record to add to the database
   */
  public void addGame(DTOGame dto) throws PropertyVetoException, SQLException {
    try (Connection connTDB = ConnectionManager.getInstance()
        .getConnection()) {
      engine.addGame(connTDB, dto);
      connTDB.commit();
    }
  }

  /**
   * Get an existing Game record from the database.
   *
   * @param key KeyGame record of the record to fetch
   * @return DTOGame record from the database
   */
  public DTOGame getGame(KeyGame key) throws PropertyVetoException, SQLException {
    try (Connection connTDB = ConnectionManager.getInstance()
        .getConnection()) {
      return engine.getGame(connTDB, key);
    }
  }

  /**
   * Update an existing record with new values.
   *
   * @param dto Record containing updated information
   * @return Record with updated information from the database
   */
  public DTOGame updateGame(DTOGame dto) throws SQLException, PropertyVetoException {
    try (Connection connTDB = ConnectionManager.getInstance()
        .getConnection()) {
      dto = engine.updateGame(connTDB, dto);
      connTDB.commit();
      return dto;
    }
  }

  /**
   * Delete the row from the database.
   *
   * @param dto Row to delete
   */
  public void deleteGame(DTOGame dto) throws SQLException, PropertyVetoException {
    try (Connection connTDB = ConnectionManager.getInstance()
        .getConnection()) {
      engine.deleteGame(connTDB, dto);
      connTDB.commit();
    }
  }

}
