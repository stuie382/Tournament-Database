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

import com.stuart.tourny.model.common.dto.DTOPlayer;
import com.stuart.tourny.model.common.key.KeyPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.stuart.tourny.model.utils.SqlUtils.getListFromResultSet;
import static com.stuart.tourny.model.utils.SqlUtils.getSFromResults;
import static com.stuart.tourny.model.utils.SqlUtils.getTSFromResults;
import static com.stuart.tourny.model.utils.SqlUtils.makeRowHash;

/**
 * <p>Class to directly handle running CRUD operations against the PLAYER table.</p><p>All methods
 * that require a {@link Connection} object to function should be passed the object by the
 * associated {@link com.stuart.tourny.controller.PlayerController} class. Similarly no
 * commits/rollbacks should occur in this class - all transaction management is the responsibility
 * of the {@link com.stuart.tourny.controller.PlayerController}.</p>
 */
public class PlayerDbEngine {

  private static final String USER_ID = "PlayerEngine";

  public PlayerDbEngine() {
    // Empty constructor
  }

  private static String getSelectSql() {
    StringBuilder sql = new StringBuilder();
    sql.append(" SELECT p.name,");
    sql.append("        p.create_datetime,");
    sql.append("        p.created_by_user_id,");
    sql.append("        p.update_datetime,");
    sql.append("        p.updated_by_user_id");
    return sql.toString();
  }

  private static String getInsertSql() {
    StringBuilder sql = new StringBuilder();
    sql.append("INSERT INTO tdb.player ( ");
    sql.append("            name,");
    sql.append("            create_datetime,");
    sql.append("            created_by_user_id,");
    sql.append("            update_datetime,");
    sql.append("            updated_by_user_id");
    sql.append(") VALUES ( ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)");
    return sql.toString();
  }

  /**
   * Attempt to retrieve a record from the player table for the given playerName
   *
   * @param conn
   *     - The connection to the database
   * @param key
   *     - Key of the record to look for
   *
   * @return DTOPlayer - Player record
   */
  public DTOPlayer getPlayer(final Connection conn,
                             final KeyPlayer key)
      throws SQLException {
    DTOPlayer dto = null;
    StringBuilder sql = new StringBuilder(getSelectSql());
    sql.append("   FROM tdb.player p ");
    sql.append("  WHERE p.name = ? ");
    try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
      int col = 1;
      ps.setString(col++, key.getPlayer());
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          dto = getDTOFromResultSet(rs);
        }
      }
    }
    if (dto == null) {
      throw new IllegalStateException("Cannot find DTOPlayer! " + key);
    }
    return dto;
  }

  /**
   * Add a new player record to the database. This will return an updated DTO back as a result.
   *
   * @param conn
   *     - The connection to the database
   * @param dto
   *     - Record to add to the database
   */
  public DTOPlayer addPlayer(final Connection conn,
                             final DTOPlayer dto) throws SQLException {

    try (PreparedStatement ps = conn.prepareStatement(getInsertSql())) {
      int col = 1;
      ps.setString(col++, dto.getName());
      ps.setString(col++, USER_ID);
      ps.setString(col++, USER_ID);
      ps.executeUpdate();
      return getPlayer(conn, new KeyPlayer(dto.getName()));
    }
  }

  /**
   * Delete a given player record.
   *
   * @param conn
   *     - The connection to the database
   * @param dto
   *     - Record to delete
   */
  public void deletePlayer(final Connection conn,
                           final DTOPlayer dto) throws SQLException {
    KeyPlayer key = dto.getKey();
    DTOPlayer oldRow = getPlayer(conn, key);
    if (!oldRow.getRowHash().equals(dto.getRowHash())) {
      throw new IllegalStateException("Delete Player failed: record changed by another process");
    }

    StringBuilder sql = new StringBuilder();
    sql.append(" DELETE FROM tdb.player p");
    sql.append("       WHERE p.name = ? ");
    try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
      int col = 1;
      ps.setString(col++, dto.getName());
      ps.executeUpdate();
    }
  }

  /**
   * Convert a ResultSet into a DTOPlayer (including creating and setting the RowHash
   */
  private DTOPlayer getDTOFromResultSet(final ResultSet rs)
      throws SQLException {
    DTOPlayer dto = new DTOPlayer();
    List<Object> results = getListFromResultSet(rs);
    dto.setRowHash(makeRowHash(results));
    int col = 0;
    dto.setName(getSFromResults(results, col++));
    dto.setCreateDatetime(getTSFromResults(results, col++));
    dto.setCreatedByUserId(getSFromResults(results, col++));
    dto.setUpdateDatetime(getTSFromResults(results, col++));
    dto.setUpdatedByUserId(getSFromResults(results, col++));
    return dto;
  }
}
