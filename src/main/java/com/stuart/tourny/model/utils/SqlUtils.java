package com.stuart.tourny.model.utils;

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
        if (input != null) {
            if (input.equalsIgnoreCase ("Y")) {
                result = true;
            }
        }
        return result;
    }

    /**
     * IF the input it true, return 'Y', else return 'N'
     *
     * @param input boolean to convert to a string representation
     * @return 'Y' if true
     */
    public static String bts (boolean input) {
        String result = "N";
        if (input) {
            result = "Y";
        }
        return result;
    }


}
