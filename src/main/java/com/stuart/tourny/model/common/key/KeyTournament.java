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
