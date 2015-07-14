package com.stuart.tourny.model.engines;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

public class QueryEngine {

  public QueryEngine() {
  }

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
