package com.stuart.tourny.model.engines;

import com.stuart.tourny.model.common.dto.DTOGame;
import com.stuart.tourny.model.common.key.KeyGame;

import java.sql.Connection;

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
     * @param key - Key of the record to return
     * @return DTOGame - Game record
     */
    public DTOGame getGame (Connection conn,
                            KeyGame key) {

        //TODO
        return null;
    }

    /**
     * Add a new game record, returning the record created.
     *
     * @param conn - The connection to the database
     * @param dto - Record to add
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
     * @param dto - Record to add
     */
    public void addGame (Connection conn,
                         DTOGame dto) {
        //TODO
    }

    /**
     * Update a given game record.
     *
     * @param conn - The connection to the database
     * @param dto - Record to update
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
     * @param dto - Record to delete
     */
    public void deleteGame (Connection conn,
                            DTOGame dto) {
        //TODO
    }
}
