package com.ceyeclon.monitoringapp.presentation.model;

import java.util.ArrayList;
import java.util.List;

public class DeviceView {

    private String ip;
    private List<DeviceStatus> deviceStatuses = new ArrayList<>();
    private DeviceState deviceState;

    public DeviceView(String ip) {
        this.ip = ip;
        for (int i = 0; i < 5; i++) {
            deviceStatuses.add(DeviceStatus.NO_DATA);
        }
    }

    public String getIp() {
        return ip;
    }

    public DeviceState getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(DeviceState deviceState) {
        this.deviceState = deviceState;
    }

    public void setStatus(int statusPosition, DeviceStatus status) {
        deviceStatuses.add(statusPosition, status);
    }

    public String getStatus(int statusPosition) {
        return deviceStatuses.get(statusPosition).toString();
    }

    public List<DeviceStatus> getDeviceStatuses() {
        return deviceStatuses;
    }
}
