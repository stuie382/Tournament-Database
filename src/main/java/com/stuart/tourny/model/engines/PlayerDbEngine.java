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
   * @param conn - The connection to the database
   * @param key  - Key of the record to look for
   * @return DTOPlayer - Player record
   */
  public DTOPlayer getPlayer(Connection conn,
                             KeyPlayer key)
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
   * @param conn - The connection to the database
   * @param dto  - Record to add to the database
   */
  public DTOPlayer addPlayer(Connection conn,
                             DTOPlayer dto) throws SQLException {

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
   * @param conn - The connection to the database
   * @param dto  - Record to delete
   */
  public void deletePlayer(Connection conn,
                           DTOPlayer dto) throws SQLException {
    StringBuilder sql = new StringBuilder();
    sql.append(" DELETE tdb.player");
    sql.append("  WHERE name = ? ");
    try (PreparedStatement ps = conn.prepareStatement(sql.toString());) {
      int col = 1;
      ps.setString(col++, dto.getName());
      ps.executeUpdate();
    }
  }

  private DTOPlayer getDTOFromResultSet(ResultSet rs)
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
