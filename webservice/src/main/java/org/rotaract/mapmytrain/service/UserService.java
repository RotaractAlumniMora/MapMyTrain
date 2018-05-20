package org.rotaract.mapmytrain.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private JsonParser jsonParser = new JsonParser();

    public String addUser(String json) {
        JsonObject user = (JsonObject) jsonParser.parse(json);
        return Constant.Status.SUCCESS;
    }
}
