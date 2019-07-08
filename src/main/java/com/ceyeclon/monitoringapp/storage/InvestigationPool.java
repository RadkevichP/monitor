package com.ceyeclon.monitoringapp.storage;

import generated.ToDevice;

import java.util.List;
import java.util.Set;

public interface InvestigationPool {

    void adToPool(String ip);

    void deleteFromPool(String ip);

    boolean isInPool(String ip);

    Set<String> getPooledDevicesIps();
}
