package com.stuart.tourny.model.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

  private static ConnectionManager connectionManager;
  private static ComboPooledDataSource cpds;
  private static final String dbURL1 = "jdbc:derby:Tournament/db1;create=true";

  /**
   * Private constructor that is run the first time that the {@code getInstance} method is run. That
   * will create the connection pool manager connected to the embedded Derby instance.
   */
  private ConnectionManager() throws PropertyVetoException {
    cpds = new ComboPooledDataSource();
    cpds.setDriverClass("org.apache.derby.jdbc.EmbeddedDataSource");
    cpds.setJdbcUrl(dbURL1);
    cpds.setMaxStatements(0);
    cpds.setInitialPoolSize(5);
    cpds.setAutoCommitOnClose(false);
  }

  /**
   * Get an instance of this connection manager. Will create a new one if it does not already
   * exist.
   *
   * @return - Connection manager
   */
  public static ConnectionManager getInstance() throws SQLException, PropertyVetoException {
    if (connectionManager == null) {
      connectionManager = new ConnectionManager();
      return connectionManager;
    } else {
      return connectionManager;
    }
  }

  /**
   * Get a connection from the pool for the specified schema, with auto commit disabled.
   *
   * @return New connection with auto commit off!
   */
  public Connection getConnection() throws SQLException {
    Connection con = cpds.getConnection();
    con.setAutoCommit(false);
    return con;
  }
}
