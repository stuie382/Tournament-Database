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

public class KeyPlayer {

  private final String player;

  /**
   * An object encapsulating the key fields from the PLAYER table.
   *
   * @param player
   *     The name field from the PLAYER record
   */
  public KeyPlayer(String player) {
    this.player = player;
  }

  public String getPlayer() {
    return player;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof KeyPlayer)) {
      return false;
    }

    KeyPlayer keyPlayer = (KeyPlayer) o;

    if (player != null ? !player.equals(keyPlayer.player) : keyPlayer.player != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return player != null ? player.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "KeyPlayer{" + "player=" + player + "}";
  }
}
