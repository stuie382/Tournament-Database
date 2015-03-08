package com.stuart.tourny.model.common.dto;


public class DTOTournament {

  public DTOTournament() {
    // Empty constructor
  }

  private int tournamentId;
  private String tournamentName;
  private String tournamentWinner;
  private String woodenSpoon;
  private String goldenBoot;
  private int goldenBootGoals;

  public int getTournamentId() {
    return tournamentId;
  }

  public void setTournamentId(int tournamentId) {
    this.tournamentId = tournamentId;
  }

  public String getTournamentName() {
    return tournamentName;
  }

  public void setTournamentName(String tournamentName) {
    this.tournamentName = tournamentName;
  }

  public String getTournamentWinner() {
    return tournamentWinner;
  }

  public void setTournamentWinner(String tournamentWinner) {
    this.tournamentWinner = tournamentWinner;
  }

  public String getWoodenSpoon() {
    return woodenSpoon;
  }

  public void setWoodenSpoon(String woodenSpoon) {
    this.woodenSpoon = woodenSpoon;
  }

  public String getGoldenBoot() {
    return goldenBoot;
  }

  public void setGoldenBoot(String goldenBoot) {
    this.goldenBoot = goldenBoot;
  }

  public int getGoldenBootGoals() {
    return goldenBootGoals;
  }

  public void setGoldenBootGoals(int goldenBootGoals) {
    this.goldenBootGoals = goldenBootGoals;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("DTOTournament{");
    sb.append("tournamentId=").append(tournamentId);
    sb.append(", tournamentName='").append(tournamentName).append('\'');
    sb.append(", tournamentWinner='").append(tournamentWinner).append('\'');
    sb.append(", woodenSpoon='").append(woodenSpoon).append('\'');
    sb.append(", goldenBoot='").append(goldenBoot).append('\'');
    sb.append(", goldenBootGoals=").append(goldenBootGoals);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DTOTournament)) {
      return false;
    }

    DTOTournament that = (DTOTournament) o;

    if (goldenBootGoals != that.goldenBootGoals) {
      return false;
    }
    if (tournamentId != that.tournamentId) {
      return false;
    }
    if (!goldenBoot.equals(that.goldenBoot)) {
      return false;
    }
    if (!tournamentName.equals(that.tournamentName)) {
      return false;
    }
    if (!tournamentWinner.equals(that.tournamentWinner)) {
      return false;
    }
    if (!woodenSpoon.equals(that.woodenSpoon)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = tournamentId;
    result = 31 * result + tournamentName.hashCode();
    result = 31 * result + tournamentWinner.hashCode();
    result = 31 * result + woodenSpoon.hashCode();
    result = 31 * result + goldenBoot.hashCode();
    result = 31 * result + goldenBootGoals;
    return result;
  }
}
