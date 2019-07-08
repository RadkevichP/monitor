package com.ceyeclon.monitoringapp.service.impl;

import com.ceyeclon.monitoringapp.service.InvestigationService;
import com.ceyeclon.monitoringapp.storage.InvestigationPool;
import generated.ToDevice;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class InvestigationServiceImpl implements InvestigationService {


    private InvestigationPool investigationPool;

    public InvestigationServiceImpl() {
    }

    @Inject
    public InvestigationServiceImpl(InvestigationPool investigationPool) {
        this.investigationPool = investigationPool;
    }

    @Override
    public void addToInvestigation(ToDevice device) {
        investigationPool.adToPool(device);
    }

    @Override
    public void deleteFromInvestigation(ToDevice device) {
        investigationPool.deleteFromPool(device);
    }

    @Override
    public boolean isUnderInvestigation(ToDevice device) {
        return investigationPool.isInPool(device);
    }

    @Override
    public List<ToDevice> getDevicesUnderInvestigation() {
        return investigationPool.getPooledDevices();
    }
}
