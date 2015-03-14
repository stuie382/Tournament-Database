package com.stuart.tourny.model.common.key;

public class KeyPlayer {

  private final String player;

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
    return "KeyPlayer{" + "playerId='" + player + '\'' + '}';
  }
}
