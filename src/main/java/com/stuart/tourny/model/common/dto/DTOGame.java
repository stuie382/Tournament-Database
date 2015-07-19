package com.stuart.tourny.model.common.dto;

import com.stuart.tourny.model.common.key.KeyGame;
import com.stuart.tourny.model.utils.SqlUtils;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>Data Transfer Object representing a single from the the GAME table. The rowHash is a composite
 * string created from the all the hashes in the DTO.</p> <p>The RowHash should only be set/checked
 * by engine methods to ensure that the row has not been changed by another process since it was
 * loaded into memory.</p>
 */
public class DTOGame implements Serializable {

  public DTOGame() {
    // Empty constructor
  }

  private long gameId;
  private String homePlayer;
  private String awayPlayer;
  private long homeGoals;
  private long awayGoals;
  private boolean extraTime;
  private long homePens;
  private long awayPens;
  private String winner;
  private long tournamentId;
  private boolean knockOut;
  private Timestamp createDatetime;
  private String createdUser;
  private Timestamp updateDatetime;
  private String updatedUser;
  private String rowHash;

  public String getRowHash() {
    return rowHash;
  }

  public long getGameId() {
    return gameId;
  }

  public String getHomePlayer() {
    return homePlayer;
  }

  public String getAwayPlayer() {
    return awayPlayer;
  }

  public long getHomeGoals() {
    return homeGoals;
  }

  public long getAwayGoals() {
    return awayGoals;
  }

  public boolean getExtraTime() {
    return extraTime;
  }

  public long getHomePens() {
    return homePens;
  }

  public long getAwayPens() {
    return awayPens;
  }

  public String getWinner() {
    return winner;
  }

  public long getTournamentId() {
    return tournamentId;
  }

  public boolean getKnockOut() {
    return knockOut;
  }

  public Timestamp getCreateDatetime() {
    return createDatetime;
  }

  public String getCreatedUser() {
    return createdUser;
  }

  public Timestamp getUpdateDatetime() {
    return updateDatetime;
  }

  public String getUpdatedUser() {
    return updatedUser;
  }

  public void setRowHash(String rowHash) {
    this.rowHash = rowHash;
  }

  public void setGameId(long gameId) {
    this.gameId = gameId;
  }

  public void setHomePlayer(String homePlayer) {
    this.homePlayer = homePlayer;
  }

  public void setAwayPlayer(String awayPlayer) {
    this.awayPlayer = awayPlayer;
  }

  public void setHomeGoals(long homeGoals) {
    this.homeGoals = homeGoals;
  }

  public void setAwayGoals(long awayGoals) {
    this.awayGoals = awayGoals;
  }

  public void setExtraTime(String extraTime) {
    this.extraTime = SqlUtils.stb(extraTime);
  }

  public void setHomePens(long homePens) {
    this.homePens = homePens;
  }

  public void setAwayPens(long awayPens) {
    this.awayPens = awayPens;
  }

  public void setWinner(String winner) {
    this.winner = winner;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DTOGame)) {
      return false;
    }

    DTOGame dtoGame = (DTOGame) o;

    if (awayGoals != dtoGame.awayGoals) {
      return false;
    }
    if (awayPens != dtoGame.awayPens) {
      return false;
    }
    if (extraTime != dtoGame.extraTime) {
      return false;
    }
    if (gameId != dtoGame.gameId) {
      return false;
    }
    if (homeGoals != dtoGame.homeGoals) {
      return false;
    }
    if (homePens != dtoGame.homePens) {
      return false;
    }
    if (knockOut != dtoGame.knockOut) {
      return false;
    }
    if (tournamentId != dtoGame.tournamentId) {
      return false;
    }
    if (!awayPlayer.equals(dtoGame.awayPlayer)) {
      return false;
    }
    if (!createDatetime.equals(dtoGame.createDatetime)) {
      return false;
    }
    if (!createdUser.equals(dtoGame.createdUser)) {
      return false;
    }
    if (!homePlayer.equals(dtoGame.homePlayer)) {
      return false;
    }
    if (!rowHash.equals(dtoGame.rowHash)) {
      return false;
    }
    if (!updateDatetime.equals(dtoGame.updateDatetime)) {
      return false;
    }
    if (!updatedUser.equals(dtoGame.updatedUser)) {
      return false;
    }
    if (!winner.equals(dtoGame.winner)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = (int) (gameId ^ (gameId >>> 32));
    result = 31 * result + homePlayer.hashCode();
    result = 31 * result + awayPlayer.hashCode();
    result = 31 * result + (int) (homeGoals ^ (homeGoals >>> 32));
    result = 31 * result + (int) (awayGoals ^ (awayGoals >>> 32));
    result = 31 * result + (extraTime ? 1 : 0);
    result = 31 * result + (int) (homePens ^ (homePens >>> 32));
    result = 31 * result + (int) (awayPens ^ (awayPens >>> 32));
    result = 31 * result + winner.hashCode();
    result = 31 * result + (int) (tournamentId ^ (tournamentId >>> 32));
    result = 31 * result + (knockOut ? 1 : 0);
    result = 31 * result + createDatetime.hashCode();
    result = 31 * result + createdUser.hashCode();
    result = 31 * result + updateDatetime.hashCode();
    result = 31 * result + updatedUser.hashCode();
    result = 31 * result + rowHash.hashCode();
    return result;
  }

  public void setTournamentId(long tournamentId) {
    this.tournamentId = tournamentId;
  }

  public void setKnockOut(String knockOut) {
    this.knockOut = SqlUtils.stb(knockOut);
  }

  public void setCreateDatetime(Timestamp createDatetime) {
    this.createDatetime = createDatetime;
  }

  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  public void setUpdateDatetime(Timestamp updateDatetime) {
    this.updateDatetime = updateDatetime;
  }

  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("DTOGame{");
    sb.append("gameId=").append(gameId);
    sb.append(", homePlayer='").append(homePlayer).append('\'');
    sb.append(", awayPlayer='").append(awayPlayer).append('\'');
    sb.append(", homeGoals=").append(homeGoals);
    sb.append(", awayGoals=").append(awayGoals);
    sb.append(", extraTime='").append(extraTime).append('\'');
    sb.append(", homePens=").append(homePens);
    sb.append(", awayPens=").append(awayPens);
    sb.append(", winner='").append(winner).append('\'');
    sb.append(", tournamentId=").append(tournamentId);
    sb.append(", knockOut='").append(knockOut).append('\'');
    sb.append(", createDatetime=").append(createDatetime);
    sb.append(", createdUser='").append(createdUser).append('\'');
    sb.append(", updateDatetime=").append(updateDatetime);
    sb.append(", updatedUser='").append(updatedUser).append('\'');
    sb.append(", rowHash='").append(rowHash).append('\'');
    sb.append('}');
    return sb.toString();
  }

  public KeyGame getKey() {
    return new KeyGame(this.gameId);
  }
}
