package com.stuart.tourny.controller;

import com.stuart.tourny.model.engines.QueryEngine;
import com.stuart.tourny.model.utils.ConnectionManager;
import com.stuart.tourny.model.utils.exceptions.ServerProblem;

import java.sql.Connection;
import java.sql.ResultSet;

public class QueryController {

  private final QueryEngine queryEngine;

  public QueryController() {
    queryEngine = new QueryEngine();
  }

  public ResultSet managePlayers_viewAll() throws ServerProblem {
    try (Connection connTDB = ConnectionManager.getInstance()
        .getConnection()) {
        return queryEngine.managePlayers_viewAll(connTDB);
    } catch (Exception ex) {
      throw new ServerProblem("Problem running Manage Players View All query: " + ex);
    }
  }

}
