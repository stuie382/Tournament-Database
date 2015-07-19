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
