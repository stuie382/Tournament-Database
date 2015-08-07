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

public class KeyGame {

  private final long gameId;

  /**
   * An object encapsulating the key fields from the GAME table.
   *
   * @param gameId
   *     The ID of the GAME record
   */
  public KeyGame(long gameId) {
    this.gameId = gameId;
  }

  public long getGameId() {
    return this.gameId;
  }

  @Override
  public String toString() {
    return "KeyGame{" + "gameId=" + gameId + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof KeyGame)) {
      return false;
    }
    KeyGame keyGame = (KeyGame) o;
    return gameId == keyGame.gameId;
  }

  @Override
  public int hashCode() {
    return (int) (gameId ^ (gameId >>> 32));
  }
}
