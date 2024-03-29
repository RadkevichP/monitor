package com.ceyeclon.presentation.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DeviceView {

    private String ip;
    private List<DeviceStatus> deviceStatuses = new ArrayList<>();
    private LocalDateTime lastPolled;
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

    public LocalDateTime getLastPolled() {
        return lastPolled;
    }

    public void setLastPolled(LocalDateTime lastPolled) {
        this.lastPolled = lastPolled;
    }

    public void setStatus(int statusPosition, DeviceStatus status) {
        deviceStatuses.set(statusPosition, status);
    }

    public String getStatus(int statusPosition) {
        return deviceStatuses.get(statusPosition).toString();
    }

    public List<DeviceStatus> getDeviceStatuses() {
        return deviceStatuses;
    }

    @Override
    public String toString() {
        return "DeviceView{" +
                "ip='" + ip + '\'' +
                ", deviceStatuses=" + deviceStatuses +
                ", deviceState=" + deviceState +
                '}';
    }
}
