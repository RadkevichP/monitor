package com.ceyeclon.monitoringapp.service;

import generated.ToDevice;

import java.util.List;
import java.util.Set;

public interface InvestigationService {

    void addToInvestigation(ToDevice device);

    void deleteFromInvestigation(ToDevice device);

    boolean isUnderInvestigation(ToDevice device);

    List<ToDevice> getDevicesUnderInvestigation();

}
