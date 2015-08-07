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
package com.stuart.tourny.model.common.key;

public class KeyTournament {

  private final long tournamentId;

  /**
   * An object encapsulating the key fields from the TOURNAMENT table.
   *
   * @param tournamentId
   *     The ID of the TOURNAMENT record
   */
  public KeyTournament(long tournamentId) {
    this.tournamentId = tournamentId;
  }

  public long getTournamentId() {
    return tournamentId;
  }

  @Override
  public String toString() {
    return "KeyTournament{" + "tournamentId=" + tournamentId + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof KeyTournament)) {
      return false;
    }
    KeyTournament that = (KeyTournament) o;
    return tournamentId == that.tournamentId;
  }

  @Override
  public int hashCode() {
    return (int) (tournamentId ^ (tournamentId >>> 32));
  }
}
