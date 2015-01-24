package com.stuart.tourny.model.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
    public static boolean stb (String input) {
        boolean result = false;
        if ((input != null) && (input.equalsIgnoreCase ("Y"))) {
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
    public static String bts (boolean input) {
        return input ? "Y" : "N";
    }

    /**
     * Method will take a list of objects and create a string representation of the hash code of
     * each object in the list.
     *
     * @param inputList - List you wish to get the hash code from
     * @return - String representation of the input list
     */
    public static String makeHashcode (List<Object> inputList) {
        StringBuilder sb = new StringBuilder ();
        for (Object item : inputList) {
            sb.append (item.hashCode ()).append ("~");
        }
        return sb.toString ();
    }

    /**
     * Will create an Object list from a result set.
     *
     * @param rs - ResultSet to convert
     * @return - Converted list
     *
     * @throws SQLException
     */
    public static List<Object> getListFromResultSet (ResultSet rs) throws SQLException {
        if (!rs.next ()) {
            return new ArrayList<> ();
        }
        List<Object> resultList = new ArrayList<> ();
        ResultSetMetaData metaData = rs.getMetaData ();
        int cols = metaData.getColumnCount ();
        for (int i = 0; i < cols; i++) {
            resultList.add (rs.getObject (i + 1));
        }
        return resultList;
    }

}
