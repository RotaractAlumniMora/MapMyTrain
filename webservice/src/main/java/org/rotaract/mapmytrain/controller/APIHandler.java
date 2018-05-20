package org.rotaract.mapmytrain.controller;

import com.google.gson.Gson;
import org.rotaract.mapmytrain.service.Constant;
import org.rotaract.mapmytrain.service.UserService;
import org.rotaract.mapmytrain.service.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class APIHandler {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{version}/{apikey}/adduser", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addUser(@PathVariable("version") String version, @PathVariable("apikey") String apiKey, @RequestBody String json) {
        if (!Util.isValidRequest(version, apiKey)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_REQUEST));
        }
        String status = userService.addUser(json);
        return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, status));
    }
}
