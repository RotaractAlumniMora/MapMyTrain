package org.rotaract.mapmytrain.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TrainService {

    private JsonParser jsonParser = new JsonParser();

    public String searchTrain(String json) {
        JsonObject searchJson = (JsonObject) jsonParser.parse(json);
        String lang = searchJson.get("lang").getAsString();
        String startStationID = searchJson.get("startStationID").getAsString();
        String endStationID = searchJson.get("endStationID").getAsString();
        String searchDate = searchJson.get("searchDate").getAsString();
        String startTime = searchJson.get("startTime").getAsString();
        String endTime = searchJson.get("endTime").getAsString();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + Util.getValue(Constant.EnvironmentVariable.ACCESSTOKEN));

        HttpEntity<String> entity = new HttpEntity<>("", headers);
        String url = Constant.RailwayAPI.SEARCH_TRAIN + "?lang=" + lang
                + "&startStationID=" + startStationID + "&endStationID=" + endStationID
                + "&searchDate=" + searchDate + "&startTime=" + startTime + "&endTime=" + endTime;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
}
