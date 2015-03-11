package com.stuart.tourny.model.engines;

import com.stuart.tourny.model.utils.ConnectionManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

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


}
