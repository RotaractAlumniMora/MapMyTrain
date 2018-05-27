package org.rotaract.mapmytrain.service;

public class Constant {

    public final static String VERSION = "v1";

    public static class Status {
        public final static String STATUS = "status";
        public final static String SUCCESS = "STATUS_SUCCESS";
        public final static String ERROR = "STATUS_ERROR";
        public final static String ERROR_REQUEST = "STATUS_ERROR_REQUEST";
        public final static String USER_ALREADY_EXISTS_ERROR = "USER_ALREADY_EXISTS";
        public final static String USER_ALREADY_SUBSCRIBED_ERROR = "USER_ALREADY_SUBSCRIBED";
    }

    public static class EnvironmentVariable {
        public final static String APIKEY = "APIKEY";
    }

}
