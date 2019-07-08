package com.ceyeclon.monitoringapp.storage;

import generated.ToDevice;

import java.util.List;

public interface InvestigationPool {

    void adToPool(ToDevice device);

    void deleteFromPool(ToDevice device);

    boolean isInPool(ToDevice device);

    List<ToDevice> getPooledDevices();
}
