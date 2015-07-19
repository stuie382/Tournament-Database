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
