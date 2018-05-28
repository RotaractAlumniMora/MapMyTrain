package org.rotaract.mapmytrain.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.rotaract.mapmytrain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class APIHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private NotificationService notificationService;

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

    @RequestMapping(value = "/{version}/{apikey}/getroutes", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRoutes(@PathVariable("version") String version, @PathVariable("apikey") String apiKey) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        List routes = subscribeService.getRoutes();
        return new Gson().toJson(Collections.singletonMap("routes", routes));
    }

    @RequestMapping(value = "/{version}/{apikey}/getstations", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getStations(@PathVariable("version") String version, @PathVariable("apikey") String apiKey, @RequestBody String json) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        List stations = subscribeService.getStations(json);
        return new Gson().toJson(Collections.singletonMap("stations", stations));
    }

    //    Comment/Notification requests

    @RequestMapping(value = "/{version}/{apikey}/gettrains", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTrains(@PathVariable("version") String version, @PathVariable("apikey") String apiKey) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        List routes = notificationService.getTrains();
        return new Gson().toJson(Collections.singletonMap("trains", routes));
    }

    @RequestMapping(value = "/{version}/{apikey}/getnewsheadlines", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getNewsHeadlines(@PathVariable("version") String version, @PathVariable("apikey") String apiKey) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        JsonArray news_headlines = notificationService.getNewsHeadlines();
        return new Gson().toJson(Collections.singletonMap("news_headlines", news_headlines));
    }

    @RequestMapping(value = "/{version}/{apikey}/getnewsdetails", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getNewsDetails(@PathVariable("version") String version, @PathVariable("apikey") String apiKey,@RequestBody String json) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        JsonObject news_details = notificationService.getNewsDeatils(json);
        return new Gson().toJson(Collections.singletonMap("news_details", news_details));
    }
}

