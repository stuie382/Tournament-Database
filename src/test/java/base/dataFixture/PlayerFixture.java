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
