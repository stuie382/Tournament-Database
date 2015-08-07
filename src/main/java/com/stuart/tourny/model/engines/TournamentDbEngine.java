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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

  static String getSelectSQL() {
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
      // Where
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

  /**
   * Get a list of all tournament names
   *
   * @param connTDB
   *     - The connection to the database
   *
   * @return - List of all tournament names
   *
   * @throws SQLException
   */
  public List<String> getTournaments(final Connection connTDB) throws SQLException {
    List<String> results = new ArrayList<>();
    StringBuilder sql = new StringBuilder();
    sql.append("   SELECT tournament_name ");
    sql.append("     FROM tdb.tournament ");
    sql.append(" ORDER BY create_datetime DESC NULLS LAST ");
    try (PreparedStatement ps = connTDB.prepareStatement(sql.toString());
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        results.add(rs.getString(1));
      }
    }
    return results;
  }

  /**
   * For the given tournament name, find the ID of that tournament.
   *
   * @param connTDB
   *     - The connection to the database
   * @param tournamentName
   *     - The name of the tournament we want the ID for
   *
   * @return - Long representing the unique ID of the tournament
   *
   * @throws SQLException
   */
  public DTOTournament getDTOTournamentFromName(Connection connTDB, String tournamentName)
      throws SQLException {
    DTOTournament dto = null;
    StringBuilder sql = new StringBuilder();
    sql.append(getSelectSQL());
    sql.append("   FROM tdb.tournament t ");
    sql.append("  WHERE t.tournament_name = ? ");
    try (PreparedStatement ps = connTDB.prepareStatement(sql.toString())) {
      ps.setString(1, tournamentName);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          dto = getDTOFromResultsSet(rs);
        }
      }
    }
    if (dto == null) {
      throw new IllegalStateException("Cannot find DTOTournament! " + tournamentName);
    }
    return dto;
  }

  /**
   * For a given tournament ID, find out the number of goals scored in a tournament (excluding
   * penalty shoot out goals) by each player. The results will be sorted in descending order
   * (highest goals scored first). Then find out which player/s scored the most and return a new map
   * of the golden goal amount and the player/s who scored that amount.
   *
   * @param connTDB
   *     - The connection to the database
   * @param tournamentId
   *     - The tournament we want to know the golden goal winner for
   *
   * @return - Map of the golden goal amount and the player/s who won
   *
   * @throws SQLException
   */
  public Map<Long, String> calculateGoldenBootForTournament(Connection connTDB,
                                                            long tournamentId)
      throws SQLException {
    Map<Long, List<String>> results = new LinkedHashMap<>();
    StringBuilder sql = new StringBuilder();
    sql.append("   SELECT sum(r.goals), ");
    sql.append("          r.player ");
    sql.append("     FROM (  ");
    sql.append("          SELECT sum(g1.home_goals) goals, ");
    sql.append("                 g1.home_player     player ");
    sql.append("            FROM tdb.game g1 ");
    sql.append("           WHERE g1.tournament_id = ? ");
    sql.append("        GROUP BY g1.home_player ");
    sql.append("       UNION ALL ");
    sql.append("          SELECT sum(g2.away_goals) goals, ");
    sql.append("                 g2.away_player     player ");
    sql.append("            FROM tdb.game g2 ");
    sql.append("           WHERE g2.tournament_id = ? ");
    sql.append("        GROUP BY g2.away_player) AS r ");
    sql.append(" GROUP BY r.player ");
    sql.append(" ORDER BY sum(r.goals) DESC ");
    try (PreparedStatement ps = connTDB.prepareStatement(sql.toString())) {
      int col = 1;
      ps.setLong(col++, tournamentId);
      ps.setLong(col++, tournamentId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          long goals = rs.getLong(1);
          String player = rs.getString(2);
          if (results.containsKey(goals)) {
            results.get(goals).add(player);
          } else {
            results.put(goals, new ArrayList<>());
            results.get(goals).add(player);
          }
        }
      }
    }
    return findGoldenBootAndPlayer(results);
  }

  /**
   * The input map is sorted into descending numerical order based on goals scored during a given
   * tournament. For the first key in the map (highest amount of goals scored), we need to create a
   * new map of the highest goals scored and the player or players who scored that amount.
   *
   * @param results
   *     - Map of goals scored and the players who scored that amount
   *
   * @return - Map of the highest number of goals scored and the player/s who scored it
   */
  private Map<Long, String> findGoldenBootAndPlayer(Map<Long, List<String>> results) {
    Map<Long, String> goldenBootAndPlayer = new HashMap<>();
    long key = results.keySet().iterator().next();
    List<String> players = results.get(key);
    if (players.size() == 1) {
      goldenBootAndPlayer.put(key, players.get(0));
    } else {
      StringBuilder goldenPlayers = new StringBuilder("TIE:");
      boolean first = true;
      for (String player : players) {
        if (first) {
          first = false;
          goldenPlayers.append(player);
        } else {
          goldenPlayers.append(", ").append(player);
        }
      }
      goldenBootAndPlayer.put(key, goldenPlayers.toString());
    }
    return goldenBootAndPlayer;
  }

}
