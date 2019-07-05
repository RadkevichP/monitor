package com.ceyeclon.monitoringapp.service;

import com.ceyeclon.monitoringapp.model.DevicePingNote;
import generated.ToDevice;

import java.util.List;

public interface PollService {

    DevicePingNote pollDevice(ToDevice device);

    List<ToDevice> findPingableDevices();
}
