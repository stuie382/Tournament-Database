package com.stuart.tourny.model.common.key;

public class KeyPlayer {

  private final long playerId;

  public KeyPlayer(long playerId) {
    this.playerId = playerId;
  }

  public long getPlayerId() {
    return playerId;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("KeyPlayer{");
    sb.append("playerId=").append(playerId);
    sb.append('}');
    return sb.toString();
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
    if (playerId != keyPlayer.playerId) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return (int) (playerId ^ (playerId >>> 32));
  }
}
