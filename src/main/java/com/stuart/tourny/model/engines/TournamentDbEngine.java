package com.stuart.tourny.model.engines;

import com.stuart.tourny.model.common.dto.DTOTournament;
import com.stuart.tourny.model.common.key.KeyTournament;

import java.sql.Connection;

public class TournamentDbEngine {

    public TournamentDbEngine () {

    }

    private static String getSelectSQL () {
        StringBuilder sql = new StringBuilder ();
        sql.append (" SELECT t.tournament_id, ");
        sql.append ("        t.tournament_name,  ");
        sql.append ("        t.tournament_winner,  ");
        sql.append ("        t.wooden_spoon,  ");
        sql.append ("        t.golden_boot,  ");
        sql.append ("        t.golden_boot_goals  ");
        return sql.toString ();
    }

    /**
     * Get a tournament record from the database.
     *
     * @param conn - The connection to the database
     * @param key  - Key of the record to look for
     * @return DTOTournament - DTO of the record
     */
    public DTOTournament getTournament (Connection conn,
                                        KeyTournament key) {
        //TODO
        return null;
    }

    /**
     * Update a tournament record.
     *
     * @param conn - The connection to the database
     * @param dto  - Record to update
     * @return DTOTournament - Record updated from the database
     */
    public DTOTournament updateRecord (Connection conn,
                                       DTOTournament dto) {
        return null;
    }

    /**
     * Add a tournament record, do not return the new record.
     *
     * @param conn - The connection to the database
     * @param dto  - Record to add
     */
    public void addRecord (Connection conn,
                           DTOTournament dto) {
    }

    /**
     * Add a tournament record, return the new record back.
     *
     * @param conn - The connection to the database
     * @param dto  - Record to add
     * @return DTOTournament - Record added
     */
    public DTOTournament addRecordAndReturn (Connection conn,
                                             DTOTournament dto) {
        return null;
    }

    /**
     * Delete a tournament record.
     *
     * @param conn - The connection to the database
     * @param dto  - Record to delete
     */
    public void deleteRecord (Connection conn,
                              DTOTournament dto) {
    }
}

