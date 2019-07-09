package com.ceyeclon.monitoringapp.service.impl;

import com.ceyeclon.monitoringapp.service.InvestigationService;
import com.ceyeclon.monitoringapp.storage.InvestigationPool;
import generated.ToDevice;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Set;

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
    public void addToInvestigation(String ip) {
        investigationPool.adToPool(ip);
    }

    @Override
    public void deleteFromInvestigation(String ip) {
        investigationPool.deleteFromPool(ip);
    }

    @Override
    public boolean isUnderInvestigation(ToDevice device) {
        return investigationPool.isInPool(device.getIp());
    }

    @Override
    public Set<String> getDevicesUnderInvestigation() {
        return investigationPool.getPooledDevicesIps();
    }
}
