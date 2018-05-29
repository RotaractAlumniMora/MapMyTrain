package org.rotaract.mapmytrain.service;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TrainService {

    public String searchTrain(String lang, String startStationID, String endStationID, String searchDate, String startTime, String endTime) {
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
