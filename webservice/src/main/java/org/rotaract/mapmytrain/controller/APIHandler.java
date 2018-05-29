package org.rotaract.mapmytrain.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.rotaract.mapmytrain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Collections;
import java.util.List;

@RestController
public class APIHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private NewsFeedService newsFeedService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private TrainService trainService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "Welcome to Map My Train !";
    }

    @RequestMapping(value = "/{version}/{apikey}/adduser", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addUser(@PathVariable("version") String version, @PathVariable("apikey") String apiKey, @RequestBody String json) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        String status = userService.addUser(json);
        return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, status));
    }

    @RequestMapping(value = "/{version}/{apikey}/updateusername", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateUserName(@PathVariable("version") String version, @PathVariable("apikey") String apiKey, @RequestBody String json) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        String status = userService.updateUserName(json);
        return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, status));
    }

    @RequestMapping(value = "/{version}/{apikey}/updateuserphone", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateUserPhone(@PathVariable("version") String version, @PathVariable("apikey") String apiKey, @RequestBody String json) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        String status = userService.updateUserPhone(json);
        return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, status));
    }

    //    Subscribe requests

    @RequestMapping(value = "/{version}/{apikey}/subscribeuser", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String subscribeUser(@PathVariable("version") String version, @PathVariable("apikey") String apiKey, @RequestBody String json) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        String response = subscribeService.subscribeUser(json);
        return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, response));
    }

    @RequestMapping(value = "/{version}/{apikey}/getroutes", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRoutes(@PathVariable("version") String version, @PathVariable("apikey") String apiKey) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        List routes = subscribeService.getRoutes();
        return new Gson().toJson(Collections.singletonMap("routes", routes));
    }

    @RequestMapping(value = "/{version}/{apikey}/getroutestations", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRouteStations(@PathVariable("version") String version, @PathVariable("apikey") String apiKey, @RequestBody String json) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        List stations = subscribeService.getRouteStations(json);
        return new Gson().toJson(Collections.singletonMap("stations", stations));
    }

    //    Comment/Notification requests

    @RequestMapping(value = "/{version}/{apikey}/getalltrains", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllTrains(@PathVariable("version") String version, @PathVariable("apikey") String apiKey) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        JsonArray trains = notificationService.getAllTrains();
        return new Gson().toJson(Collections.singletonMap("trains", trains));
    }


    @RequestMapping(value = "/{version}/{apikey}/getroutetrains", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRouteTrains(@PathVariable("version") String version, @PathVariable("apikey") String apiKey, @RequestBody String json) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        JsonArray trains = notificationService.getRouteTrains(json);
        return new Gson().toJson(Collections.singletonMap("trains", trains));
    }


    @RequestMapping(value = "/{version}/{apikey}/gettrainstations", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTrainStations(@PathVariable("version") String version, @PathVariable("apikey") String apiKey, @RequestBody String json) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        List stations = notificationService.getTrainStations(json);
        return new Gson().toJson(Collections.singletonMap("stations", stations));
    }

    @RequestMapping(value = "/{version}/{apikey}/addnews", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addNews(@PathVariable("version") String version, @PathVariable("apikey") String apiKey, @RequestBody String json) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        String status = notificationService.addNews(json);
        return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, status));
    }


    // News Feed requests

    @RequestMapping(value = "/{version}/{apikey}/getnewsheadlines", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getNewsHeadlines(@PathVariable("version") String version, @PathVariable("apikey") String apiKey, @RequestBody String json) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        JsonArray news_headlines = newsFeedService.getNewsHeadlines(json);
        return new Gson().toJson(Collections.singletonMap("news_headlines", news_headlines));
    }

    @RequestMapping(value = "/{version}/{apikey}/getnewsdetails", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getNewsDetails(@PathVariable("version") String version, @PathVariable("apikey") String apiKey, @RequestBody String json) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        JsonObject news_details = newsFeedService.getNewsDetails(json);
        return new Gson().toJson(Collections.singletonMap("news_details", news_details));
    }

    // Railway API v1.0

    @RequestMapping(value = "/{version}/{apikey}/searchtrain", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String searchTrain(@PathVariable("version") String version, @PathVariable("apikey") String apiKey,
                              @PathParam("lang") String lang, @PathParam("startStationID") String startStationID,
                              @PathParam("endStationID") String endStationID, @PathParam("searchDate") String searchDate,
                              @PathParam("startTime") String startTime, @PathParam("endTime") String endTime) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        return trainService.searchTrain(lang, startStationID, endStationID, searchDate, startTime, endTime);
    }
}

