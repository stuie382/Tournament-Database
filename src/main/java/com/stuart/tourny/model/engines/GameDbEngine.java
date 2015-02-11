package com.stuart.tourny.model.engines;

import com.stuart.tourny.model.common.dto.DTOGame;
import com.stuart.tourny.model.common.key.KeyGame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.stuart.tourny.model.utils.SqlUtils.bts;
import static com.stuart.tourny.model.utils.SqlUtils.getListFromResultSet;
import static com.stuart.tourny.model.utils.SqlUtils.getLongFromResults;
import static com.stuart.tourny.model.utils.SqlUtils.getSFromResults;
import static com.stuart.tourny.model.utils.SqlUtils.getTSFromResults;
import static com.stuart.tourny.model.utils.SqlUtils.makeRowHash;

public class GameDbEngine {
    private static final String USER_ID = "GameEngine";
    private static String getSelectSql () {
        StringBuilder sql = new StringBuilder ();
        sql.append (" SELECT g.game_id, ");
        sql.append ("        g.home_player, ");
        sql.append ("        g.away_player, ");
        sql.append ("        g.home_goals, ");
        sql.append ("        g.away_goals, ");
        sql.append ("        g.extra_time, ");
        sql.append ("        g.home_pens, ");
        sql.append ("        g.away_pens, ");
        sql.append ("        g.winner, ");
        sql.append ("        g.tournament_id, ");
        sql.append ("        g.knock_out ");
        return sql.toString ();
    }

    private static String getInsertSql () {
        StringBuilder sql = new StringBuilder ();
        sql.append ("INSERT INTO tdb.game ( ");
        sql.append ("                  home_player,");
        sql.append ("                  away_player,");
        sql.append ("                  home_goals,");
        sql.append ("                  away_goals,");
        sql.append ("                  extra_time,");
        sql.append ("                  home_pens,");
        sql.append ("                  away_pens,");
        sql.append ("                  winner,");
        sql.append ("                  tournament_id,");
        sql.append ("                  knock_out,");
        sql.append ("                  updated_by_user_id,");
        sql.append ("                  update_datetime,");
        sql.append ("                  created_by_user_id,");
        sql.append ("                  create_datetime");
        sql.append (") VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, SYSDATE)");
        return sql.toString ();
    }

    private static String getUpdateSql () {
        StringBuilder sql = new StringBuilder ();
        sql.append ("  UPDATE tdb.game g ");
        sql.append ("     SET g.home_player = ?  ");
        sql.append ("        g.away_player = ?  ");
        sql.append ("        g.home_goals = ?  ");
        sql.append ("        g.away_goals = ?  ");
        sql.append ("        g.extra_time = ?  ");
        sql.append ("        g.home_pens = ?  ");
        sql.append ("        g.away_pens = ?  ");
        sql.append ("        g.winner = ?  ");
        sql.append ("        g.tournament_id = ? ");
        sql.append ("  WHERE g.game_id = ? ");
        return sql.toString ();
    }

    /**
     * Get a game record matching the key object.
     *
     * @param connTDB - The connection to the database
     * @param key     - Key of the record to return
     * @return DTOGame - Game record
     */
    public DTOGame getGame (Connection connTDB,
                            KeyGame key) throws SQLException {
        DTOGame dto = null;
        StringBuilder sql = new StringBuilder (getSelectSql ());
        sql.append ("  FROM tdb.game g ");
        sql.append (" WHERE g.game_id = ? ");
        try (PreparedStatement ps = connTDB.prepareStatement (sql.toString ())) {
            int col = 1;
            ps.setLong (col, key.getGameId ());
            try (ResultSet rs = ps.executeQuery ()) {
                if (rs.next ()) {
                    dto = getDTOFromResultSet (rs);
                }
            }
        }
        if (dto == null) {
            throw new IllegalArgumentException ("Could not find Game record for " + key);
        }
        return dto;
    }

    /**
     * Create a new DTOGame from a result set. Needs to set the hash code of the DTO.
     *
     * @param rs ResultSet to convert into a DTO
     * @return populated DTOGame object
     *
     * @throws SQLException
     */
    private DTOGame getDTOFromResultSet (ResultSet rs) throws SQLException {
        DTOGame dto = new DTOGame ();

        List<Object> results = getListFromResultSet (rs);
        dto.setRowHash (makeRowHash (results));
        int col = 0;
        dto.setGameId (getLongFromResults (results, col++));
        dto.setHomePlayer (getSFromResults (results, col++));
        dto.setAwayPlayer (getSFromResults (results, col++));
        dto.setHomeGoals (getLongFromResults (results, col++));
        dto.setAwayGoals (getLongFromResults (results, col++));
        dto.setExtraTime (getSFromResults (results, col++));
        dto.setHomePens (getLongFromResults (results, col++));
        dto.setAwayPens (getLongFromResults (results, col++));
        dto.setWinner (getSFromResults (results, col++));
        dto.setTournamentId (getLongFromResults (results, col++));
        dto.setKnockOut (getSFromResults (results, col++));
        dto.setUpdatedUser (getSFromResults (results, col++));
        dto.setUpdateDatetime (getTSFromResults (results, col++));
        dto.setCreatedUser (getSFromResults (results, col++));
        dto.setCreateDatetime (getTSFromResults (results, col++));
        return dto;
    }

    /**
     * Add a new game record, returning the record created.
     *
     * @param connTDB - The connection to the database
     * @param dto     - Record to add
     * @return DTOGame - Record added
     */
    public DTOGame addGameAndReturn (Connection connTDB,
                                     DTOGame dto) {
        //TODO
        return null;
    }

    /**
     * Add a new game record, no result is returned.
     *
     * @param connTDB - The connection to the database
     * @param dto     - Record to add
     */
    public void addGame (Connection connTDB,
                         DTOGame dto) throws SQLException {
        String sql = getInsertSql ();
        try (PreparedStatement ps = connTDB.prepareStatement (sql,new String[]{"ID_COLUMN"})) {
            int col = 1;
            ps.setString (col++, dto.getHomePlayer ());
            ps.setString(col++,dto.getAwayPlayer());
            ps.setLong (col++,dto.getHomeGoals());
            ps.setLong( col++,dto.getAwayGoals());
            ps.setString(col++,bts (dto.getExtraTime ()));
            ps.setLong(col++,dto.getHomePens());
            ps.setLong( col++,dto.getAwayPens());
            ps.setString(col++,dto.getWinner());
            ps.setLong( col++,dto.getTournamentId());
            ps.setString(col++,bts (dto.getKnockOut ()));
            ps.setString(col++,USER_ID);
            ps.setString(col++,USER_ID);

            ps.executeUpdate ();
            ResultSet rs = ps.getGeneratedKeys ();
        }
    }

    /**
     * Update a given game record.
     *
     * @param connTDB - The connection to the database
     * @param dto     - Record to update
     * @return DTOGame - Record updated from the database
     */
    public DTOGame updateGame (Connection connTDB,
                               DTOGame dto) {

        //TODO
        return null;
    }

    /**
     * Delete a given game record.
     *
     * @param connTDB - The connection to the database
     * @param dto     - Record to delete
     */
    public void deleteGame (Connection connTDB,
                            DTOGame dto)
            throws SQLException {
        KeyGame key = dto.getKey ();
        DTOGame oldRow = getGame (connTDB, key);
        if (!oldRow.getRowHash ().equals (dto.getRowHash ())) {
            throw new IllegalStateException ("delete Game: record changed by another process");
        }
        StringBuilder sql = new StringBuilder ();
        sql.append (" DELETE game");
        try (PreparedStatement ps = connTDB.prepareStatement (sql.toString ())) {
            ps.executeUpdate ();
        }
    }
}
