package com.ceyeclon.monitoringapp.service;

import generated.ToDevice;

import java.util.Set;

public interface InvestigationService {

    void addToInvestigation(String ip);

    void deleteFromInvestigation(String ip);

    boolean isUnderInvestigation(ToDevice device);

    Set<String> getDevicesUnderInvestigation();

}
