package org.rotaract.mapmytrain.controller;

import com.google.gson.Gson;
import org.rotaract.mapmytrain.service.Constant;
import org.rotaract.mapmytrain.service.Feature;
import org.rotaract.mapmytrain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class APIHandler {

    @Autowired
    private Feature feature;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{version}/adduser", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addUser(@PathVariable("version") String version, @RequestBody String json) {
        if (!feature.validateRequest(version)) {
            return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, Constant.Status.ERROR_VERSION));
        }
        return new Gson().toJson(Collections.singletonMap(Constant.Status.STATUS, userService.addUser(json)));
    }
}
