package org.rotaract.mapmytrain.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.rotaract.mapmytrain.dao.User;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private JsonParser jsonParser = new JsonParser();

    public String addUser(String json) {
        JsonObject userJson = (JsonObject) jsonParser.parse(json);
        User user = new User();
        user.setName(userJson.get("name").getAsString());
        user.setEmail(userJson.get("email").getAsString());

        System.out.println(user.toString());
        // user -> database

        return Constant.Status.SUCCESS;
    }
}
