package com.stuart.tourny.controller;

import com.stuart.tourny.model.common.dto.DTOPlayer;
import com.stuart.tourny.model.engines.PlayerDbEngine;
import com.stuart.tourny.model.utils.ConnectionManager;
import com.stuart.tourny.model.utils.exceptions.ServerProblem;

import org.apache.log4j.Logger;

import java.sql.Connection;

public class PlayerController {

  private static final Logger log = Logger.getLogger(PlayerController.class);

  private final PlayerDbEngine engine;

  public PlayerController() {
    engine = new PlayerDbEngine();
  }

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


}
