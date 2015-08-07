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
package base.dataFixture;

import com.stuart.tourny.model.common.dto.DTOPlayer;
import com.stuart.tourny.model.engines.PlayerDbEngine;

import java.sql.Connection;
import java.sql.SQLException;

public class PlayerFixture {

  private PlayerDbEngine playerEngine;

  public PlayerFixture() {
    playerEngine = new PlayerDbEngine();
  }

  /**
   * Add a simple {@code Player} record with the given {@code name} attribute.
   *
   * @param conn
   *     - Connection to the database
   * @param name
   *     - name to set on the record
   *
   * @return - Populated {@code DTOPlayer} record from the database
   *
   * @throws java.sql.SQLException
   */
  public DTOPlayer addAPlayer(Connection conn,
                              String name) throws SQLException {
    DTOPlayer dto = new DTOPlayer();
    dto.setName(name);
    dto = playerEngine.addPlayer(conn, dto);
    return dto;
  }

}
