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
package com.stuart.tourny.model.engines;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

/**
 * <p>Class to directly handle running custom queries against the TDB schema.</p><p>All methods that
 * require a {@link Connection} object to function should be passed the object by the associated
 * {@link com.stuart.tourny.controller.QueryController} class. Similarly no commits/rollbacks should
 * occur in this class - all transaction management is the responsibility of the {@link
 * com.stuart.tourny.controller.QueryController}.</p>
 */
public class QueryEngine {

  public QueryEngine() {
    //Empty Constructor
  }

  /**
   * Find all players currently stored in the database and return them in alphabetical order.
   *
   * @param connTDB
   *     - The connection to the database
   *
   * @return rowSet - CachedRowSet representing the data found by the query
   *
   * @throws SQLException
   */
  public CachedRowSet managePlayers_viewAll(Connection connTDB) throws SQLException {
    CachedRowSet rowSet = new CachedRowSetImpl();
    StringBuilder sql = new StringBuilder();
    sql.append(" SELECT p.name ");
    sql.append("   FROM tdb.player p ");
    sql.append(" ORDER BY p.name ASC ");
    try (PreparedStatement ps = connTDB.prepareStatement(sql.toString());
         ResultSet rs = ps.executeQuery()) {
      rowSet.populate(rs);
      return rowSet;
    }
  }

  /**
   * Return all games played across all tournaments, ordered by tournament name, then player name.
   *
   * @param connTDB
   *     - The connection to the database
   *
   * @return rowSet - CachedRowSet representing the data found by the query
   *
   * @throws SQLException
   */
  public CachedRowSet manageGames_viewAll(Connection connTDB) throws SQLException {
    CachedRowSet rowSet = new CachedRowSetImpl();
    StringBuilder sql = new StringBuilder();
    sql.append(GameDbEngine.getSelectSql());
    sql.append("   FROM tdb.game g ");
    sql.append("  ORDER BY g.home_player ");
    try (PreparedStatement ps = connTDB.prepareStatement(sql.toString());
         ResultSet rs = ps.executeQuery()) {
      rowSet.populate(rs);
      return rowSet;
    }
  }
}
