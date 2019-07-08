package com.ceyeclon.monitoringapp.storage.impl;

import com.ceyeclon.monitoringapp.storage.InvestigationPool;
import generated.ToDevice;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class InvestigationPoolImpl implements InvestigationPool {

    private Map<String, ToDevice> devicesUnderInvestigation = new HashMap<>();

    @Override
    @Lock(LockType.WRITE)
    public void adToPool(ToDevice device) {
        devicesUnderInvestigation.put(device.getIp(), device);
    }

    @Override
    @Lock(LockType.WRITE)
    public void deleteFromPool(ToDevice device) {
        devicesUnderInvestigation.remove(device.getIp());
    }

    @Override
    @Lock(LockType.READ)
    public boolean isInPool(ToDevice device) {
        return devicesUnderInvestigation.containsKey(device.getIp());
    }

    @Override
    @Lock(LockType.READ)
    public List<ToDevice> getPooledDevices() {
        return devicesUnderInvestigation.values()
                .stream()
                .collect(Collectors.toList());
    }

}
