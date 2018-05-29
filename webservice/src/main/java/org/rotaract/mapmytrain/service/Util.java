package org.rotaract.mapmytrain.service;

public class Util {

    public static String getValue(String variableName) {
        if (System.getProperty(variableName) != null) {
            return System.getProperty(variableName);
        } else if (System.getenv(variableName) != null) {
            return System.getenv(variableName);
        }
        return null;
    }

    public static boolean isValidRequest(String version, String apiKey) {
        return version.equals(Constant.VERSION) && apiKey.equals(Constant.Credentials.APIKEY);
    }
}
