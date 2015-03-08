package com.stuart.tourny.model.utils;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SqlUtils {

  /**
   * If the input is 'Y' then return true, else return false. Will also return false if a null
   * string is passed in.
   *
   * @param input String we want to convert
   * @return true if input is Y
   */
  public static boolean stb(String input) {
    boolean result = false;
    if ((input != null) && (input.equalsIgnoreCase("Y"))) {
      result = true;
    }
    return result;
  }

  /**
   * If the input it true, return 'Y', else return 'N'
   *
   * @param input boolean to convert to a string representation
   * @return 'Y' if true
   */
  public static String bts(boolean input) {
    return input ? "Y" : "N";
  }

  /**
   * Method will take a list of objects and create a string representation of the hash code of each
   * object in the list.
   *
   * @param inputList - List you wish to get the hash code from
   * @return - String representation of the input list
   */
  public static String makeRowHash(List<Object> inputList) {
    if (inputList.isEmpty()) {
      return Constants.EMPTY_STRING;
    }
    final String SEPARATOR = "~";
    StringBuilder sb = new StringBuilder();
    for (Object item : inputList) {
      if (item == null) {
        sb.append(SEPARATOR);
      } else {
        if (item instanceof Clob || item instanceof Blob) {
          // Can't hash code so skip it
        } else {
          sb.append(item.hashCode());
          sb.append(SEPARATOR);
        }
      }
    }
    return sb.toString();
  }

  /**
   * Will create an Object list from a result set.
   *
   * @param rs - ResultSet to convert
   * @return - Converted list
   */
  public static List<Object> getListFromResultSet(ResultSet rs) throws SQLException {
    if (!rs.next()) {
      return new ArrayList<>();
    }
    List<Object> resultList = new ArrayList<>();
    ResultSetMetaData metaData = rs.getMetaData();
    int counter = metaData.getColumnCount();

    for (int i = 0; i < counter; i++) {
      String columnType = metaData.getColumnTypeName(i);
      if (Constants.DATE.equals(columnType)) {
        resultList.add(rs.getTimestamp(i));
      } else {
        resultList.add(rs.getObject(i));
      }
    }
    return resultList;
  }

  /**
   * Get a String from the results list. May return null if the location contains a null.
   *
   * @param in  Object List from the result set
   * @param loc location in the list
   * @return String from the location
   */
  public static String getSFromResults(List<Object> in, int loc) {
    Object o = in.get(loc);
    if (o == null) {
      return null;
    } else {
      return o.toString();
    }
  }

  /**
   * Get a Timestamp from the results list. May return null if the location contains a null.
   *
   * @param in  Object List from the result set
   * @param loc location in the list
   * @return Timestamp from the location
   */
  public static Timestamp getTSFromResults(List<Object> in, int loc) {
    Object o = in.get(loc);
    if (o == null) {
      return null;
    } else {
      return (Timestamp) o;
    }
  }

  /**
   * Get a long from the results list. May return -1 if the location contains a null.
   *
   * @param in  Object List from the result set
   * @param loc location in the list
   * @return long from the location
   */
  public static long getLongFromResults(List<Object> in, int loc) {
    Object o = in.get(loc);
    if (o == null) {
      return -1;
    } else {
      if (o instanceof BigDecimal) {
        return ((BigDecimal) o).longValue();
      } else if (o instanceof Integer) {
        return ((Integer) o).longValue();
      } else {
        return Long.parseLong(o.toString());
      }
    }
  }

  /**
   * Get a BigDecimal from the results list. May return null if the location contains a null.
   *
   * @param in  Object List from the result set
   * @param loc location in the list
   * @return BigDecimal from the location
   */
  public static BigDecimal getBigDecimalFromResults(List<Object> in, int loc) {
    Object o = in.get(loc);
    if (o == null) {
      return null;
    } else {
      return (BigDecimal) o;
    }
  }

  /**
   * Get a boolean from the results list. Will return false if the location contains a null.
   *
   * @param in  Object List from the result set
   * @param loc location in the list
   * @return boolean from the location
   */
  public static boolean getBoolFromResults(List<Object> in, int loc) {
    String str = getSFromResults(in, loc);
    if (str == null) {
      return false;
    }
    return str.equalsIgnoreCase("Y");
  }
}
