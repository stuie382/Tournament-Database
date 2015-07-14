package com.stuart.tourny.model.engines;

import com.stuart.tourny.model.common.dto.DTOTournament;
import com.stuart.tourny.model.common.key.KeyTournament;
import com.stuart.tourny.model.utils.ConnectionManager;
import com.stuart.tourny.model.utils.dataFixture.TournamentFixture;

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
  private TournamentFixture fixture;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

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
    fixture = new TournamentFixture();
  }

  @After
  public void tearDown() throws Exception {
    connTDB.rollback();
  }

  /**
   * Add a new fully populated tournament
   * @throws SQLException
   */
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

  /**
   * Create an initial tournament with just a name, then add a winner, then a runner up, then golden
   * boot, all as separate transactions.
   *
   * @throws SQLException
   */
  @Test
  public void testUpdateATournament() throws SQLException {
    DTOTournament dtoToUpdate = fixture.addATournament(connTDB, "March 2015", null, null, null, -1);
    assertNotNull(dtoToUpdate);
    long tournamentId = dtoToUpdate.getTournamentId();

    // Test a series of incremental updates
    dtoToUpdate.setTournamentWinner("Stuart");
    DTOTournament updated = uut.updateRecord(connTDB, dtoToUpdate);
    assertEquals(tournamentId, updated.getTournamentId());
    assertNotNull(updated);
    assertEquals("Stuart", updated.getTournamentWinner());

    updated.setWoodenSpoon("SpoonFace");
    updated = uut.updateRecord(connTDB, updated);
    assertEquals(tournamentId, updated.getTournamentId());
    assertEquals("SpoonFace", updated.getWoodenSpoon());

    updated.setGoldenBoot("Wonderkid");
    updated.setGoldenBootGoals(52);
    updated = uut.updateRecord(connTDB, updated);
    assertEquals(tournamentId, updated.getTournamentId());
    assertEquals(52, updated.getGoldenBootGoals());
    assertEquals("Wonderkid", updated.getGoldenBoot());
  }

  @Test
  public void testDeleteATournament()
      throws Exception {
    DTOTournament dto = fixture.addATournament(connTDB, "March 2015", null, null, null, -1);
    assertNotNull(dto);

    long tournamentId = dto.getTournamentId();
    uut.deleteRecord(connTDB, dto);

    thrown.expect(IllegalStateException.class);
    thrown.expectMessage(
        "Cannot find DTOTournament! KeyTournament{tournamentId=" + tournamentId + "}");
    uut.getTournament(connTDB, new KeyTournament(tournamentId));
  }
}
