package com.stuart.tourny.model.engines;

import com.stuart.tourny.model.common.dto.DTOGame;
import com.stuart.tourny.model.common.key.KeyGame;
import com.stuart.tourny.model.utils.SqlUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.stuart.tourny.model.utils.SqlUtils.getListFromResultSet;
import static com.stuart.tourny.model.utils.SqlUtils.makeHashcode;

public class GameDbEngine {

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
        sql.append (" INSERT INTO game (g.home_player, ");
        sql.append ("                   g.away_player, ");
        sql.append ("                   g.home_goals, ");
        sql.append ("                   g.away_goals, ");
        sql.append ("                   g.extra_time, ");
        sql.append ("                   g.home_pens, ");
        sql.append ("                   g.away_pens, ");
        sql.append ("                   g.winner, ");
        sql.append ("                   g.tournament_id, ");
        sql.append ("                   g.knock_out) ");
        sql.append (" VALUES (?,?,?,?,?,?,?,?,?,? ");
        return sql.toString ();
    }

    /**
     * Get a game record matching the key object.
     *
     * @param connTDB - The connection to the database
     * @param key  - Key of the record to return
     * @return DTOGame - Game record
     */
    public DTOGame getGame (Connection connTDB,
                            KeyGame key) throws SQLException {
        DTOGame dto = null;
        StringBuilder sql = new StringBuilder (getSelectSql ());
        sql.append ("  FROM game g ");
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
        int col = 1;
        dto.setHashCode (makeHashcode (getListFromResultSet (rs)));
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
        return dto;
    }

    /**
     * Add a new game record, returning the record created.
     *
     * @param connTDB - The connection to the database
     * @param dto  - Record to add
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
     * @param dto  - Record to add
     */
    public void addGame (Connection connTDB,
                         DTOGame dto) {
        //TODO
    }

    /**
     * Update a given game record.
     *
     * @param connTDB - The connection to the database
     * @param dto  - Record to update
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
     * @param dto  - Record to delete
     */
    public void deleteGame (Connection connTDB,
                            DTOGame dto) {
        //TODO
    }
}
