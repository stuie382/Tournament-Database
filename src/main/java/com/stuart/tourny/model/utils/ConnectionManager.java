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
package com.stuart.tourny.model.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton ConnectionManager to deal with the embedded Derby database.
 */
public class ConnectionManager {

  private static ConnectionManager connectionManager;
  private static ComboPooledDataSource dataSource;
  private static final String dbURL1 = "jdbc:derby:Tournament/db1;create=true";

  /**
   * Private constructor that is run the first time that the {@code getInstance} method is run. That
   * will create the connection pool manager connected to the embedded Derby instance.
   */
  private ConnectionManager() throws PropertyVetoException {
    Properties p = System.getProperties();
    p.put("derby.language.logStatementText","true");
    System.setProperties(p);

    dataSource = new ComboPooledDataSource();
    dataSource.setDriverClass("org.apache.derby.jdbc.EmbeddedDataSource");
    dataSource.setJdbcUrl(dbURL1);
    dataSource.setMaxStatements(0);
    dataSource.setInitialPoolSize(5);
    dataSource.setAutoCommitOnClose(false);
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
    Connection con = dataSource.getConnection();
    con.setAutoCommit(false);
    return con;
  }
}
