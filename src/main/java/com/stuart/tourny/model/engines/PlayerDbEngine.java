package com.stuart.tourny.model.engines;

import com.stuart.tourny.model.common.dto.DTOPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerDbEngine {

  public PlayerDbEngine() {
    // Empty constructor
  }

  private static String getSelectSql() {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT p.name ");
    return sql.toString();
  }

  /**
   * Attempt to retrieve a record from the player table for the given playerName
   *
   * @param con        - The connection to the database
   * @param playerName - Key of the record to look for
   * @return DTOPlayer - Player record
   */
  public DTOPlayer getPlayer(Connection con,
                             String playerName) {

    //TODO implement this
    return null;
  }

  /**
   * Add a new player record to the database. This will not return an updated DTO back as a result.
   *
   * @param con - The connection to the database
   * @param dto - Record to add to the database
   */
  public void addPlayer(Connection con,
                        DTOPlayer dto) {
    StringBuilder sql = new StringBuilder();
    sql.append(" INSERT INTO app.player ");
    sql.append(" VALUES ? ");

    try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
      ps.setString(1, dto.getName());
      ps.executeUpdate();
    } catch (SQLException ex) {
      System.out.println("Error adding player: " + ex.getMessage());
    }
  }

  /**
   * Update a given player record.
   *
   * @param conn - The connection to the database
   * @param dto  - Record to update
   * @return - Updated record from the database
   */
  public DTOPlayer updatePlayer(Connection conn,
                                DTOPlayer dto) {
    //TODO
    return null;
  }

  /**
   * Delete a given player record.
   *
   * @param conn - The connection to the database
   * @param dto  - Record to delete
   */
  public void deletePlayer(Connection conn,
                           DTOPlayer dto) {
    //TODO
  }


}
