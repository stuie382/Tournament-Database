package com.stuart.tourny.controller;

import com.stuart.tourny.model.engines.QueryEngine;
import com.stuart.tourny.model.utils.ConnectionManager;
import com.stuart.tourny.model.utils.exceptions.ServerProblem;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * <p>Controller class that will manage the {@link Connection} objects used to run select queries
 * against the TDB schema.</p><p> <strong>This class should not make any commits!</strong><p/> Each
 * method should log at DEBUG/ERROR levels when a transaction completes/exception occurs.</p>
 * <p>Nominally each method will be returning a ResultSet that can be used by the {@link
 * com.stuart.tourny.view.ResultSetTablePanel}, but that is a suggested convention rather than a
 * hard rule. </p>
 */
public class QueryController {

  private static final Logger log = Logger.getLogger(QueryController.class);
  private final QueryEngine queryEngine;

  public QueryController() {
    queryEngine = new QueryEngine();
  }

  public ResultSet managePlayers_viewAll() throws ServerProblem {
    try (Connection connTDB = ConnectionManager.getInstance()
        .getConnection()) {
      log.debug("Attempting to run managePlayers_viewAll...");
      return queryEngine.managePlayers_viewAll(connTDB);
    } catch (Exception ex) {
      String error = "Problem running Manage Players View All query: " + ex;
      log.error(error);
      throw new ServerProblem(error);
    }
  }

}
