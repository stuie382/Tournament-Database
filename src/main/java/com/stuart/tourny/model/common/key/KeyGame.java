package com.stuart.tourny.model.common.key;

public class KeyGame {

  private final long gameId;

  public KeyGame(long gameId) {
    this.gameId = gameId;
  }

  public long getGameId() {
    return this.gameId;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("KeyGame{");
    sb.append("gameId=").append(gameId);
    sb.append('}');
    return sb.toString();
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
    if (gameId != keyGame.gameId) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return (int) (gameId ^ (gameId >>> 32));
  }
}
