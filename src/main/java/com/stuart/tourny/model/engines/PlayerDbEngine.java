package com.stuart.tourny.model.engines;

import com.stuart.tourny.model.dto.DTOPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerDbEngine {

    public PlayerDbEngine () {
        // Empty constructor
    }

    private static String getSelectSql () {
        StringBuilder sql = new StringBuilder ();
        sql.append ("SELECT p.name ");
        return sql.toString ();
    }

    /**
     * Attempt to retrieve a record from the player table for the given
     * playerName
     * @param con
     * @param playerName
     * @return
     */
    public DTOPlayer getPlayer (Connection con,
                                String playerName) {

        //TODO implement this
        return null;
    }

    /**
     * Add a new player record to the database. This will not return an
     * updated DTO back as a result.
     * @param con
     * @param dto
     */
    public void addPlayer (Connection con,
                           DTOPlayer dto) {
        StringBuilder sql = new StringBuilder ();
        sql.append (" INSERT INTO app.player ");
        sql.append (" VALUES ? ");

        try (PreparedStatement ps = con.prepareStatement (sql.toString ())) {
            ps.setString (1, dto.getName ());
            ps.executeUpdate ();
        } catch (SQLException ex) {
            System.out.println ("Error adding player: " + ex.getMessage ());
        }
    }


}
