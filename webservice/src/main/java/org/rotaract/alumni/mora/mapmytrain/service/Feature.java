package org.rotaract.alumni.mora.mapmytrain.service;

import org.springframework.stereotype.Component;

@Component
public class Feature {

    public boolean validateRequest(String version) {
        return version.equals(Constant.VERSION);
    }
}
