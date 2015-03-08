package com.stuart.tourny.model.common.key;

public class KeyTournament {

  private final long tournamentId;

  public KeyTournament(long tournamentId) {
    this.tournamentId = tournamentId;
  }

  public long getTournamentId() {
    return tournamentId;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("KeyTournament{");
    sb.append("tournamentId=").append(tournamentId);
    sb.append('}');
    return sb.toString();
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
    if (tournamentId != that.tournamentId) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return (int) (tournamentId ^ (tournamentId >>> 32));
  }
}
