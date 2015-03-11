package com.stuart.tourny.model.engines;

import com.stuart.tourny.model.common.dto.DTOTournament;
import com.stuart.tourny.model.common.key.KeyTournament;

import java.sql.Connection;

public class TournamentDbEngine {

  public TournamentDbEngine() {

  }

  private static String getSelectSQL() {
    StringBuilder sql = new StringBuilder();
    sql.append(" SELECT t.tournament_id, ");
    sql.append("        t.tournament_name,  ");
    sql.append("        t.tournament_winner,  ");
    sql.append("        t.wooden_spoon,  ");
    sql.append("        t.golden_boot,  ");
    sql.append("        t.golden_boot_goals  ");
    return sql.toString();
  }

  private static String getInsertSql() {
    StringBuilder sql = new StringBuilder();
    sql.append("INSERT INTO tournament ( ");
    sql.append("                        tournament_id,");
    sql.append("                        tournament_name,");
    sql.append("                        tournament_winner,");
    sql.append("                        wooden_spoon,");
    sql.append("                        golden_boot,");
    sql.append("                        golden_boot_goals,");
    sql.append("                        create_datetime,");
    sql.append("                        created_by_user_id,");
    sql.append("                        update_datetime,");
    sql.append("                        updated_by_user_id");
    sql.append(") VALUES ( ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)");
    return sql.toString();
  }

  private static String getUpdateSql() {
    StringBuilder sql = new StringBuilder();
    sql.append(" UPDATE tournament");
    sql.append("    SET tournament_name = ? ,");
    sql.append("        tournament_winner = ? ,");
    sql.append("        wooden_spoon = ? ,");
    sql.append("        golden_boot = ? ,");
    sql.append("        golden_boot_goals = ? ,");
    sql.append("        update_datetime = sysdate ,");
    sql.append("        updated_by_user_id = ? ");
    sql.append("  WHERE tournament_id = ? ");
    sql.append("   AND update_datetime = ? ");
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
  public DTOTournament getTournament(Connection conn,
                                     KeyTournament key) {
    //TODO
    return null;
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
  public DTOTournament updateRecord(Connection conn,
                                    DTOTournament dto) {
    return null;
  }

  /**
   * Add a tournament record, do not return the new record.
   *
   * @param conn
   *     - The connection to the database
   * @param dto
   *     - Record to add
   */
  public void addRecord(Connection conn,
                        DTOTournament dto) {
  }

  /**
   * Add a tournament record, return the new record back.
   *
   * @param conn
   *     - The connection to the database
   * @param dto
   *     - Record to add
   *
   * @return DTOTournament - Record added
   */
  public DTOTournament addRecordAndReturn(Connection conn,
                                          DTOTournament dto) {
    return null;
  }

  /**
   * Delete a tournament record.
   *
   * @param conn
   *     - The connection to the database
   * @param dto
   *     - Record to delete
   */
  public void deleteRecord(Connection conn,
                           DTOTournament dto) {
  }
}

