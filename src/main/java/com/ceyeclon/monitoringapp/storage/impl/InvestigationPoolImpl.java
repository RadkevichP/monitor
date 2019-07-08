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

    private Set<String> ipsUnderInvestigation = new HashSet<>();

    @Override
    @Lock(LockType.WRITE)
    public void adToPool(String ip) {
        ipsUnderInvestigation.add(ip);
    }

    @Override
    @Lock(LockType.WRITE)
    public void deleteFromPool(String ip) {
        ipsUnderInvestigation.remove(ip);
    }

    @Override
    @Lock(LockType.READ)
    public boolean isInPool(String ip) {
        return ipsUnderInvestigation.contains(ip);
    }

    @Override
    @Lock(LockType.READ)
    public Set<String> getPooledDevicesIps() {
        return ipsUnderInvestigation;
    }

}
