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
package com.stuart.tourny.controller;

import com.stuart.tourny.model.engines.TournamentDbEngine;

import org.apache.log4j.Logger;

import java.sql.Connection;

/**
 * <p>Controller class that will manage the {@link Connection} objects used to query against the
 * TOURNAMENT table.</p><p> This should handle any commits required and should log at DEBUG/ERROR
 * levels when a transaction completes/exception occurs.</p>
 */
public class TournamentController {

  private static final Logger log = Logger.getLogger(TournamentController.class);

  private final TournamentDbEngine engine;

  public TournamentController() {
    engine = new TournamentDbEngine();
  }

 // public DTOTournament addTournament(DTOTournament dto ) throws ServerProblem {
   ///
 // }
}
