package com.stuart.tourny.model.engines;

import com.stuart.tourny.model.common.dto.DTOTournament;
import com.stuart.tourny.model.utils.ConnectionManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class TournamentDbEngineTest {

  private static Connection connTDB;
  private TournamentDbEngine uut;

  @BeforeClass
  public static void beforeSetUp() throws SQLException, PropertyVetoException {
    connTDB = ConnectionManager.getInstance().getConnection();
    connTDB.setSavepoint();
  }

  @AfterClass
  public static void afterTearDown() throws SQLException {
    connTDB.close();
  }

  @Before
  public void setUp() throws Exception {
    uut = new TournamentDbEngine();
  }

  @After
  public void tearDown() throws Exception {
    connTDB.rollback();
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testAddATournament() throws SQLException {
    DTOTournament dto = new DTOTournament();
    dto.setTournamentName("Jan 2015");
    dto.setTournamentWinner("Stuart");
    dto.setWoodenSpoon("Spoon");
    dto.setGoldenBoot("Boot");
    dto.setGoldenBootGoals(21);
    DTOTournament newDto = uut.addRecord(connTDB, dto);

    assertNotNull(newDto.getTournamentId());
    assertEquals("Jan 2015", newDto.getTournamentName());
    assertEquals("Stuart", newDto.getTournamentWinner());
    assertEquals("Spoon", newDto.getWoodenSpoon());
    assertEquals("Boot", newDto.getGoldenBoot());
    assertEquals(21, newDto.getGoldenBootGoals());
    assertEquals("TournamentEngine", newDto.getCreatedByUserId());
    assertEquals("TournamentEngine", newDto.getUpdatedByUserId());

    assertNotNull(newDto.getCreateDatetime());
    assertNotNull(newDto.getUpdateDatetime());
  }

}
