package com.ceyeclon.monitoringapp.config;

import generated.MLZStateAPI_Service;

import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;

@Stateless
public class AppConfig {

    @Produces
    public MLZStateAPI_Service mlzStateAPI_service() {
        return new MLZStateAPI_Service();
    }
}
