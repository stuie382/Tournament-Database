package com.stuart.tourny.model.engines;

import com.stuart.tourny.model.common.key.KeyGame;
import com.stuart.tourny.model.utils.ConnectionManager;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.Connection;

public class GameDbEngineTest extends TestCase {

    private Connection conn;
    private GameDbEngine uut;


    @Before
    public void setUp () throws Exception {
        conn = ConnectionManager.getInstance ().getConnection ();
        uut = new GameDbEngine ();
    }

    @After
    public void tearDown () throws Exception {
        conn.close ();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none ();

    @Test (expected = IllegalArgumentException.class)
    public void testGetGame () throws Exception {
        KeyGame key = new KeyGame (99999);
        exception.expect (IllegalArgumentException.class);
        exception.expectMessage ("Could not find Game record for KeyGame{gameId=99999}");
        uut.getGame (conn, key);
    }

    @Test
    public void testAddGameAndReturn () throws Exception {

    }

    @Test
    public void testAddGame () throws Exception {

    }

    @Test
    public void testUpdateGame () throws Exception {

    }

    @Test
    public void testDeleteGame () throws Exception {

    }
}
