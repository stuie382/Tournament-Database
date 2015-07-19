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
   * @param conn
   *     - The connection to the database
   *
   * @return rowSet - CachedRowSet representing the data found by the query
   *
   * @throws SQLException
   */
  public CachedRowSet managePlayers_viewAll(Connection conn) throws SQLException {
    CachedRowSet rowSet = new CachedRowSetImpl();
    StringBuilder sql = new StringBuilder();
    sql.append(" SELECT p.name ");
    sql.append("   FROM tdb.player p ");
    sql.append(" ORDER BY p.name ASC ");
    try (PreparedStatement ps = conn.prepareStatement(sql.toString());
         ResultSet rs = ps.executeQuery()) {
      rowSet.populate(rs);
      return rowSet;
    }
  }
}
