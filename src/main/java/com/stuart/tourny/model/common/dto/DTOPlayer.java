package com.stuart.tourny.model.common.dto;

import com.stuart.tourny.model.common.key.KeyPlayer;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>Data Transfer Object representing a single from the the PLAYER table. The rowHash is a composite
 * string created from the all the hashes in the DTO.</p> <p>The RowHash should only be set/checked
 * by engine methods to ensure that the row has not been changed by another process since it was
 * loaded into memory.</p>
 */
public class DTOPlayer implements Serializable {

  private String name;
  private Timestamp createDatetime;
  private Timestamp updateDatetime;
  private String createdByUserId;
  private String updatedByUserId;
  private String rowHash;

  public DTOPlayer() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DTOPlayer)) {
      return false;
    }

    DTOPlayer dtoPlayer = (DTOPlayer) o;

    if (createDatetime != null ? !createDatetime.equals(dtoPlayer.createDatetime)
                               : dtoPlayer.createDatetime != null) {
      return false;
    }
    if (createdByUserId != null ? !createdByUserId.equals(dtoPlayer.createdByUserId)
                                : dtoPlayer.createdByUserId != null) {
      return false;
    }
    if (name != null ? !name.equals(dtoPlayer.name) : dtoPlayer.name != null) {
      return false;
    }
    if (rowHash != null ? !rowHash.equals(dtoPlayer.rowHash) : dtoPlayer.rowHash != null) {
      return false;
    }
    if (updateDatetime != null ? !updateDatetime.equals(dtoPlayer.updateDatetime)
                               : dtoPlayer.updateDatetime != null) {
      return false;
    }
    if (updatedByUserId != null ? !updatedByUserId.equals(dtoPlayer.updatedByUserId)
                                : dtoPlayer.updatedByUserId != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (createDatetime != null ? createDatetime.hashCode() : 0);
    result = 31 * result + (updateDatetime != null ? updateDatetime.hashCode() : 0);
    result = 31 * result + (createdByUserId != null ? createdByUserId.hashCode() : 0);
    result = 31 * result + (updatedByUserId != null ? updatedByUserId.hashCode() : 0);
    result = 31 * result + (rowHash != null ? rowHash.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("DTOPlayer{");
    sb.append("name='").append(name).append('\'');
    sb.append(", createDatetime=").append(createDatetime);
    sb.append(", updateDatetime=").append(updateDatetime);
    sb.append(", createdByUserId='").append(createdByUserId).append('\'');
    sb.append(", updatedByUserId='").append(updatedByUserId).append('\'');
    sb.append(", rowHash='").append(rowHash).append('\'');
    sb.append('}');
    return sb.toString();
  }

  public KeyPlayer getKey() {
    return new KeyPlayer(this.name);
  }
}
