package com.stuart.tourny.model.engines;

import com.stuart.tourny.model.common.dto.DTOTournament;
import com.stuart.tourny.model.common.key.KeyTournament;
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

import base.TestBase;
import base.dataFixture.TournamentFixture;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class TournamentDbEngineTest extends TestBase {

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
   *
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

  /**
   * Try to perform an update on an existing tournament where the row hash has been changed.
   *
   * @throws SQLException
   */
  @Test
  public void testUpdateATournament_RowHashProblem() throws SQLException {
    DTOTournament dtoToUpdate = fixture.addATournament(connTDB, "March 2015", null, null, null, -1);
    assertNotNull(dtoToUpdate);

    // Intended incremental update
    dtoToUpdate.setTournamentWinner("Stuart");

    // Break the Row Hash
    dtoToUpdate.setRowHash("Broken");

    thrown.expect(IllegalStateException.class);
    thrown.expectMessage(
        "Update Tournament: record changed by another process");
    uut.updateRecord(connTDB, dtoToUpdate);
  }

  /**
   * Simple test to delete a record from the database.
   *
   * @throws Exception
   */
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

  @Test
  public void testUpdateATournament_UpdatePrimaryKey() throws SQLException {
    DTOTournament dtoToUpdate = fixture.addATournament(connTDB, "March 2015", null, null, null, -1);
    assertNotNull(dtoToUpdate);

    // Intended incremental update
    dtoToUpdate.setTournamentId(999999);

    thrown.expect(IllegalStateException.class);
    thrown.expectMessage(
        "Cannot find DTOTournament! KeyTournament{tournamentId=999999}");
    uut.updateRecord(connTDB, dtoToUpdate);
  }

  /**
   * Simple test to show that the system will throw the expected error when the row hash in memory
   * and on disk are different.
   *
   * @throws Exception
   */
  @Test
  public void testDeleteATournament_RowHashProblem()
      throws Exception {
    DTOTournament dto = fixture.addATournament(connTDB, "March 2015", null, null, null, -1);
    assertNotNull(dto);
    assertEquals("March 2015", dto.getTournamentName());
    dto.setRowHash("Broken");
    // This will now throw an exception as it thinks the in-memory record
    // and on disk record are different.
    thrown.expect(IllegalStateException.class);
    thrown.expectMessage(
        "Delete Tournament: record changed by another process");
    uut.deleteRecord(connTDB, dto);
  }
}
