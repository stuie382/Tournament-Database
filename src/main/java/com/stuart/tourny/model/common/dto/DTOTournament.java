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
package com.stuart.tourny.model.common.dto;


import com.stuart.tourny.model.common.key.KeyTournament;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>Data Transfer Object representing a single from the the TOURNAMENT table. The rowHash is a
 * composite string created from the all the hashes in the DTO.</p> <p>The RowHash should only be
 * set/checked by engine methods to ensure that the row has not been changed by another process
 * since it was loaded into memory.</p>
 */
public class DTOTournament implements Serializable {

  public DTOTournament() {
    // Empty constructor
  }

  private long tournamentId;
  private String tournamentName;
  private String tournamentWinner;
  private String woodenSpoon;
  private String goldenBoot;
  private long goldenBootGoals;
  private Timestamp createDatetime;
  private Timestamp updateDatetime;
  private String createdByUserId;
  private String updatedByUserId;
  private String rowHash;

  public long getTournamentId() {
    return tournamentId;
  }

  public void setTournamentId(long tournamentId) {
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

  public long getGoldenBootGoals() {
    return goldenBootGoals;
  }

  public void setGoldenBootGoals(long goldenBootGoals) {
    this.goldenBootGoals = goldenBootGoals;
  }

  public Timestamp getCreateDatetime() {
    return createDatetime;
  }

  public void setCreateDatetime(Timestamp createDatetime) {
    this.createDatetime = createDatetime;
  }

  public Timestamp getUpdateDatetime() {
    return updateDatetime;
  }

  public void setUpdateDatetime(Timestamp updateDatetime) {
    this.updateDatetime = updateDatetime;
  }

  public String getCreatedByUserId() {
    return createdByUserId;
  }

  public void setCreatedByUserId(String createdByUserId) {
    this.createdByUserId = createdByUserId;
  }

  public String getUpdatedByUserId() {
    return updatedByUserId;
  }

  public void setUpdatedByUserId(String updatedByUserId) {
    this.updatedByUserId = updatedByUserId;
  }

  public String getRowHash() {
    return rowHash;
  }

  public void setRowHash(String rowHash) {
    this.rowHash = rowHash;
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
    sb.append(", createDatetime=").append(createDatetime);
    sb.append(", updateDatetime=").append(updateDatetime);
    sb.append(", createdByUserId='").append(createdByUserId).append('\'');
    sb.append(", updatedByUserId='").append(updatedByUserId).append('\'');
    sb.append(", rowHash='").append(rowHash).append('\'');
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
    if (createDatetime != null ? !createDatetime.equals(that.createDatetime)
                               : that.createDatetime != null) {
      return false;
    }
    if (createdByUserId != null ? !createdByUserId.equals(that.createdByUserId)
                                : that.createdByUserId != null) {
      return false;
    }
    if (goldenBoot != null ? !goldenBoot.equals(that.goldenBoot) : that.goldenBoot != null) {
      return false;
    }
    if (rowHash != null ? !rowHash.equals(that.rowHash) : that.rowHash != null) {
      return false;
    }
    if (tournamentName != null ? !tournamentName.equals(that.tournamentName)
                               : that.tournamentName != null) {
      return false;
    }
    if (tournamentWinner != null ? !tournamentWinner.equals(that.tournamentWinner)
                                 : that.tournamentWinner != null) {
      return false;
    }
    if (updateDatetime != null ? !updateDatetime.equals(that.updateDatetime)
                               : that.updateDatetime != null) {
      return false;
    }
    if (updatedByUserId != null ? !updatedByUserId.equals(that.updatedByUserId)
                                : that.updatedByUserId != null) {
      return false;
    }
    if (woodenSpoon != null ? !woodenSpoon.equals(that.woodenSpoon) : that.woodenSpoon != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = (int) (tournamentId ^ (tournamentId >>> 32));
    result = 31 * result + (tournamentName != null ? tournamentName.hashCode() : 0);
    result = 31 * result + (tournamentWinner != null ? tournamentWinner.hashCode() : 0);
    result = 31 * result + (woodenSpoon != null ? woodenSpoon.hashCode() : 0);
    result = 31 * result + (goldenBoot != null ? goldenBoot.hashCode() : 0);
    result = 31 * result + (int) (goldenBootGoals ^ (goldenBootGoals >>> 32));
    result = 31 * result + (createDatetime != null ? createDatetime.hashCode() : 0);
    result = 31 * result + (updateDatetime != null ? updateDatetime.hashCode() : 0);
    result = 31 * result + (createdByUserId != null ? createdByUserId.hashCode() : 0);
    result = 31 * result + (updatedByUserId != null ? updatedByUserId.hashCode() : 0);
    result = 31 * result + (rowHash != null ? rowHash.hashCode() : 0);
    return result;
  }


  public KeyTournament getKey() {
    return new KeyTournament(tournamentId);
  }
}
