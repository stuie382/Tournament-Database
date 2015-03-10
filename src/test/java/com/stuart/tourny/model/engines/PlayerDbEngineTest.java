package com.stuart.tourny.model.engines;

import com.stuart.tourny.model.common.dto.DTOPlayer;
import com.stuart.tourny.model.utils.ConnectionManager;
import com.stuart.tourny.model.utils.dataFixture.PlayerFixture;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.Connection;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class PlayerDbEngineTest {

  private Connection connTDB;
  private PlayerDbEngine uut;
  private PlayerFixture playerFixture;


  @Before
  public void setUp() throws Exception {
    connTDB = ConnectionManager.getInstance().getConnection();
    uut = new PlayerDbEngine();
    playerFixture = new PlayerFixture();
  }

  @After
  public void tearDown() throws Exception {
    connTDB.close();
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testAddPlayer()
      throws Exception {
    DTOPlayer dto = new DTOPlayer();
    dto.setName("Stuart");
    dto = uut.addPlayer(connTDB, dto);
    assertNotNull(dto);
    assertEquals("Stuart", dto.getName());
    assertEquals("PlayerEngine", dto.getCreatedByUserId());
    assertEquals("PlayerEngine", dto.getUpdatedByUserId());
    assertNotNull(dto.getCreateDatetime());
    assertNotNull(dto.getUpdateDatetime());

  }
}
