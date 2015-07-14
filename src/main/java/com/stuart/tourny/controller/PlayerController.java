package com.stuart.tourny.controller;

import com.stuart.tourny.model.common.dto.DTOPlayer;
import com.stuart.tourny.model.engines.PlayerDbEngine;
import com.stuart.tourny.model.utils.ConnectionManager;
import com.stuart.tourny.model.utils.exceptions.ServerProblem;

import java.sql.Connection;

public class PlayerController {

  private final PlayerDbEngine engine;

  public PlayerController() {
    engine = new PlayerDbEngine();
  }

  public DTOPlayer addPlayer(DTOPlayer dto) throws ServerProblem {
    try (Connection connTDB = ConnectionManager.getInstance()
        .getConnection()) {
      dto = engine.addPlayer(connTDB, dto);
      connTDB.commit();
      return dto;
    } catch (Exception ex) {
      throw new ServerProblem("Problem encountered adding a player: " + ex);
    }
  }


}
