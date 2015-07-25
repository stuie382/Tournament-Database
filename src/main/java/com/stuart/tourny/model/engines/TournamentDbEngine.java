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

import com.stuart.tourny.model.common.dto.DTOTournament;
import com.stuart.tourny.model.common.key.KeyTournament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.stuart.tourny.model.utils.SqlUtils.getListFromResultSet;
import static com.stuart.tourny.model.utils.SqlUtils.getLongFromResults;
import static com.stuart.tourny.model.utils.SqlUtils.getSFromResults;
import static com.stuart.tourny.model.utils.SqlUtils.getTSFromResults;
import static com.stuart.tourny.model.utils.SqlUtils.makeRowHash;
import static com.stuart.tourny.model.utils.SqlUtils.maybeNull;

/**
 * <p>Class to directly handle running CRUD operations against the TOURNAMENT table.</p><p>All
 * methods that require a {@link Connection} object to function should be passed the object by the
 * associated {@link com.stuart.tourny.controller.TournamentController} class. Similarly no
 * commits/rollbacks should occur in this class - all transaction management is the responsibility
 * of the {@link com.stuart.tourny.controller.TournamentController}.</p>
 */
public class TournamentDbEngine {

  private static final String USER_ID = "TournamentEngine";

  public TournamentDbEngine() {
    // Empty constructor
  }

  private static String getSelectSQL() {
    StringBuilder sql = new StringBuilder();
    sql.append(" SELECT t.tournament_id, ");
    sql.append("        t.tournament_name,  ");
    sql.append("        t.tournament_winner,  ");
    sql.append("        t.wooden_spoon,  ");
    sql.append("        t.golden_boot,  ");
    sql.append("        t.golden_boot_goals,  ");
    sql.append("        t.create_datetime, ");
    sql.append("        t.created_by_user_id, ");
    sql.append("        t.update_datetime, ");
    sql.append("        t.updated_by_user_id ");
    return sql.toString();
  }

  private static String getInsertSql() {
    StringBuilder sql = new StringBuilder();
    sql.append("INSERT INTO tdb.tournament ( ");
    sql.append("                tournament_name,");
    sql.append("                tournament_winner,");
    sql.append("                wooden_spoon,");
    sql.append("                golden_boot,");
    sql.append("                golden_boot_goals,");
    sql.append("                create_datetime,");
    sql.append("                created_by_user_id,");
    sql.append("                update_datetime,");
    sql.append("                updated_by_user_id");
    sql.append(") VALUES ( ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)");
    return sql.toString();
  }

  private static String getUpdateSql() {
    StringBuilder sql = new StringBuilder();
    sql.append(" UPDATE tdb.tournament");
    sql.append("    SET tournament_name = ? ,");
    sql.append("        tournament_winner = ? ,");
    sql.append("        wooden_spoon = ? ,");
    sql.append("        golden_boot = ? ,");
    sql.append("        golden_boot_goals = ? ,");
    sql.append("        update_datetime = CURRENT_TIMESTAMP ,");
    sql.append("        updated_by_user_id = ? ");
    sql.append("  WHERE tournament_id = ? ");
    return sql.toString();
  }

  /**
   * Get a tournament record from the database.
   *
   * @param conn
   *     - The connection to the database
   * @param key
   *     - Key of the record to look for
   *
   * @return DTOTournament - DTO of the record
   */
  public DTOTournament getTournament(final Connection conn,
                                     final KeyTournament key) throws SQLException {
    DTOTournament dto = null;

    StringBuilder sql = new StringBuilder(getSelectSQL());
    sql.append("   FROM tdb.tournament t ");
    sql.append("  WHERE t.tournament_id = ? ");
    try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
      int col = 1;
      ps.setLong(col++, key.getTournamentId());
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          dto = getDTOFromResultsSet(rs);
        }
      }
    }
    if (dto == null) {
      throw new IllegalStateException("Cannot find DTOTournament! " + key);
    }
    return dto;
  }

  /**
   * Update a tournament record.
   *
   * @param conn
   *     - The connection to the database
   * @param dto
   *     - Record to update
   *
   * @return DTOTournament - Record updated from the database
   */
  public DTOTournament updateRecord(final Connection conn,
                                    final DTOTournament dto) throws SQLException {
    KeyTournament key = dto.getKey();
    DTOTournament oldRow = getTournament(conn, key);
    if (!oldRow.getRowHash().equals(dto.getRowHash())) {
      throw new IllegalStateException("Update Tournament: record changed by another process");
    }
    try (PreparedStatement ps = conn.prepareStatement(getUpdateSql())) {
      int col = 1;
      ps.setString(col++, dto.getTournamentName());
      ps.setString(col++, dto.getTournamentWinner());
      ps.setString(col++, dto.getWoodenSpoon());
      ps.setString(col++, dto.getGoldenBoot());
      ps.setLong(col++, maybeNull(dto.getGoldenBootGoals()));
      ps.setString(col++, USER_ID);
      ps.setLong(col++, dto.getTournamentId());

      if (ps.executeUpdate() == 0) {
        throw new IllegalStateException("Update tournament failed: " + key.toString());
      }
    }
    return getTournament(conn, key);
  }

  /**
   * Add a tournament record, do not return the new record.
   *
   * @param conn
   *     - The connection to the database
   * @param dto
   *     - Record to add
   */
  public DTOTournament addRecord(final Connection conn,
                                 final DTOTournament dto) throws SQLException {

    String sql = getInsertSql();
    try (PreparedStatement ps = conn.prepareStatement(sql,
                                                      Statement.RETURN_GENERATED_KEYS)) {
      int col = 1;
      ps.setString(col++, dto.getTournamentName());
      ps.setString(col++, dto.getTournamentWinner());
      ps.setString(col++, dto.getWoodenSpoon());
      ps.setString(col++, dto.getGoldenBoot());
      ps.setLong(col++, maybeNull(dto.getGoldenBootGoals()));
      ps.setString(col++, USER_ID);
      ps.setString(col++, USER_ID);
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        long key = -1;
        if (rs.next()) {
          key = rs.getLong(1);
        }
        return getTournament(conn, new KeyTournament(key));
      }
    }
  }

  /**
   * Delete a tournament record.
   *
   * @param conn
   *     - The connection to the database
   * @param dto
   *     - Record to delete
   */

  public void deleteRecord(final Connection conn,
                           final DTOTournament dto) throws SQLException {
    KeyTournament key = dto.getKey();
    DTOTournament oldRow = getTournament(conn, key);
    if (!oldRow.getRowHash().equals(dto.getRowHash())) {
      throw new IllegalStateException("Delete Tournament: record changed by another process");
    }
    StringBuilder sql = new StringBuilder();
    sql.append(" DELETE FROM tdb.tournament");
    sql.append("       WHERE tournament_id = ? ");
    try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
      int col = 1;
      ps.setLong(col++, key.getTournamentId());
      ps.executeUpdate();
    }
  }

  private DTOTournament getDTOFromResultsSet(final ResultSet rs) throws SQLException {
    DTOTournament dto = new DTOTournament();
    List<Object> results = getListFromResultSet(rs);
    dto.setRowHash(makeRowHash(results));
    int col = 0;
    dto.setTournamentId(getLongFromResults(results, col++));
    dto.setTournamentName(getSFromResults(results, col++));
    dto.setTournamentWinner(getSFromResults(results, col++));
    dto.setWoodenSpoon(getSFromResults(results, col++));
    dto.setGoldenBoot(getSFromResults(results, col++));
    dto.setGoldenBootGoals(getLongFromResults(results, col++));
    dto.setCreateDatetime(getTSFromResults(results, col++));
    dto.setCreatedByUserId(getSFromResults(results, col++));
    dto.setUpdateDatetime(getTSFromResults(results, col++));
    dto.setUpdatedByUserId(getSFromResults(results, col++));
    return dto;
  }
}

