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
        public final static String ACCESSTOKEN = "ACCESSTOKEN";
    }

    public static class RailwayAPI {
        public final static String SEARCH_TRAIN = "http://api.lankagate.gov.lk:8280/railway/1.0/train/searchTrain";
    }

    public static class Credentials {
        public final static String APIKEY = "C461D3C23C7E7264726A8D1DD5E";
        public final static String ACCESSTOKEN = "7a69c7ee-1c5f-395b-9a84-f51b22537e04";
    }

}
