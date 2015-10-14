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

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class containing method for use with {@code PreparedStatement} and
 * {@code ResultSet} manipulation.
 */
public final class SqlUtils {

    private SqlUtils() {
	// Private constructor to prevent instantiation.
    }

    /**
     * If the input is 'Y' then return true, else return false. Will also return
     * false if a null string is passed in.
     *
     * @param input
     *            String we want to convert
     *
     * @return true if input is Y
     */
    public static boolean stb(final String input) {
	boolean result = false;
	if ((input != null) && ("Y".equalsIgnoreCase(input))) {
	    result = true;
	}
	return result;
    }

    /**
     * If the input it true, return 'Y', else return 'N'
     *
     * @param input
     *            boolean to convert to a string representation
     *
     * @return 'Y' if true
     */
    public static String bts(final boolean input) {
	return input ? "Y" : "N";
    }

    /**
     * Method will take a list of objects and create a string representation of
     * the hash code of each object in the list.
     *
     * @param inputList
     *            - List you wish to get the hash code from
     *
     * @return - String representation of the input list
     */
    public static String makeRowHash(final List<Object> inputList) {
	if (inputList.isEmpty()) {
	    return Constants.EMPTY_STRING;
	}
	final String separator = "~";
	StringBuilder sb = new StringBuilder();
	for (Object item : inputList) {
	    if (item == null) {
		sb.append(separator);
	    } else {
		if (!(item instanceof Clob) && (!(item instanceof Blob))) {
		    sb.append(item.hashCode());
		    sb.append(separator);
		}
		// Can't hash code so skip it
	    }
	}
	return sb.toString();
    }

    /**
     * Will create an Object list from a result set.
     *
     * @param rs
     *            - ResultSet to convert
     *
     * @return - Converted list
     */
    public static List<Object> getListFromResultSet(final ResultSet rs) throws SQLException {
	List<Object> resultList = new ArrayList<>();
	ResultSetMetaData metaData = rs.getMetaData();
	int counter = metaData.getColumnCount();

	for (int i = 1; i <= counter; i++) {
	    String columnType = metaData.getColumnTypeName(i);
	    if (Constants.DATE.equals(columnType) || Constants.TIMESTAMP.equals(columnType)) {
		resultList.add(rs.getTimestamp(i));
	    } else {
		resultList.add(rs.getObject(i));
	    }
	}
	return resultList;
    }

    /**
     * Get a String from the results list. May return null if the location
     * contains a null.
     *
     * @param in
     *            Object List from the result set
     * @param loc
     *            location in the list
     *
     * @return String from the location
     */
    public static String getSFromResults(final List<Object> in, final int loc) {
	Object o = in.get(loc);
	if (o == null) {
	    return null;
	}
	return o.toString();
    }

    /**
     * Get a Timestamp from the results list. May return null if the location
     * contains a null.
     *
     * @param in
     *            Object List from the result set
     * @param loc
     *            location in the list
     *
     * @return Timestamp from the location
     */
    public static Timestamp getTSFromResults(final List<Object> in, final int loc) {
	Object o = in.get(loc);
	if (o == null) {
	    return null;
	}
	return (Timestamp) o;
    }

    /**
     * Get a long from the results list. May return -1 if the location contains
     * a null.
     *
     * @param in
     *            Object List from the result set
     * @param loc
     *            location in the list
     *
     * @return long from the location
     */
    public static long getLongFromResults(final List<Object> in, final int loc) {
	Object o = in.get(loc);
	if (o == null) {
	    return -1;
	}
	if (o instanceof BigDecimal) {
	    return ((BigDecimal) o).longValue();
	} else if (o instanceof Integer) {
	    return ((Integer) o).longValue();
	} else {
	    return Long.parseLong(o.toString());
	}
    }

    /**
     * Get a BigDecimal from the results list. May return null if the location
     * contains a null.
     *
     * @param in
     *            Object List from the result set
     * @param loc
     *            location in the list
     *
     * @return BigDecimal from the location
     */
    public static BigDecimal getBigDecimalFromResults(final List<Object> in, final int loc) {
	Object o = in.get(loc);
	if (o == null) {
	    return null;
	}
	return (BigDecimal) o;
    }

    /**
     * Get a boolean from the results list. Will return false if the location
     * contains a null.
     *
     * @param in
     *            Object List from the result set
     * @param loc
     *            location in the list
     *
     * @return boolean from the location
     */
    public static boolean getBoolFromResults(final List<Object> in, final int loc) {
	String str = getSFromResults(in, loc);
	return (str != null) && "Y".equalsIgnoreCase(str);
    }

    /**
     * Where a {@code Long} is used, return -1 if it is a null value.
     *
     * @param input
     *            Long value to be checked
     *
     * @return long version of the {@code input} if it is not null, else return
     *         -1
     */
    public static long maybeNull(final Long input) {
	if (input == null) {
	    return -1;
	}
	return input;
    }
}
