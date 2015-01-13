package com.stuart.tourny.model.engines;

import com.stuart.tourny.model.common.dto.DTOGame;
import com.stuart.tourny.model.common.key.KeyGame;
import com.stuart.tourny.model.utils.SqlUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDbEngine {

    private static String getSelectSQL () {
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

    /**
     * Get a game record matching the key object.
     *
     * @param conn - The connection to the database
     * @param key  - Key of the record to return
     * @return DTOGame - Game record
     */
    public DTOGame getGame (Connection conn,
                            KeyGame key) throws SQLException {
        DTOGame dto = null;
        StringBuilder sql = new StringBuilder (getSelectSQL ());
        sql.append ("  FROM game ");
        sql.append (" WHERE game_id = ? ");
        try (PreparedStatement ps = conn.prepareStatement (sql.toString ())) {
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
        int col = 1;
        dto.setGameId (rs.getLong (col++));
        dto.setHomePlayer (rs.getString (col++));
        dto.setAwayPlayer (rs.getString (col++));
        dto.setHomeGoals (rs.getLong (col++));
        dto.setAwayGoals (rs.getLong (col++));
        dto.setExtraTime (SqlUtils.stb (rs.getString (col++)));
        dto.setHomePens (rs.getLong (col++));
        dto.setAwayPens (rs.getLong (col++));
        dto.setWinner (rs.getString (col++));
        dto.setTournamentId (rs.getLong (col++));
        dto.setKnockOut (SqlUtils.stb (rs.getString (col++)));
        dto.setHashCode (dto.hashCode ());
        //TODO hashcode??
        return dto;
    }

    /**
     * Add a new game record, returning the record created.
     *
     * @param conn - The connection to the database
     * @param dto  - Record to add
     * @return DTOGame - Record added
     */
    public DTOGame addGameAndReturn (Connection conn,
                                     DTOGame dto) {
        //TODO
        return null;
    }

    /**
     * Add a new game record, no result is returned.
     *
     * @param conn - The connection to the database
     * @param dto  - Record to add
     */
    public void addGame (Connection conn,
                         DTOGame dto) {
        //TODO
    }

    /**
     * Update a given game record.
     *
     * @param conn - The connection to the database
     * @param dto  - Record to update
     * @return DTOGame - Record updated from the database
     */
    public DTOGame updateGame (Connection conn,
                               DTOGame dto) {
        //TODO
        return null;
    }

    /**
     * Delete a given game record.
     *
     * @param conn - The connection to the database
     * @param dto  - Record to delete
     */
    public void deleteGame (Connection conn,
                            DTOGame dto) {
        //TODO
    }
}
