package main.java.com.stuart.tourny.model.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

    private static ConnectionManager conn;
    private static ComboPooledDataSource cpds;
    private static final String dbURL1 = "jdbc:derby:Tournament/db1;create=true";

    private ConnectionManager () throws PropertyVetoException {
        // private constructor
        cpds = new ComboPooledDataSource ();
        cpds.setDriverClass ("org.apache.derby.jdbc.EmbeddedDataSource");
        cpds.setJdbcUrl (dbURL1);
        cpds.setMaxStatements (50);
        cpds.setInitialPoolSize (5);
        cpds.setAutoCommitOnClose (false);

    }

    public static ConnectionManager getInstance () throws SQLException, PropertyVetoException {
        if (conn == null) {
            conn = new ConnectionManager ();
            return conn;
        }
        else {
            return conn;
        }
    }

    /**
     * Get a connection from the pool, with auto commit disabled.
     *
     * @return New connection with auto commit off!
     *
     * @throws SQLException
     */
    public Connection getConnection () throws SQLException {
        Connection con = this.cpds.getConnection ();
        con.setAutoCommit (false);
        return con;
    }
}
