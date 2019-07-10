package com.ceyeclon.monitoringapp.presentation;

import java.util.ArrayList;
import java.util.List;

public class DeviceView {

    private String ip;
    private List<DeviceStatus> deviceStatuses = new ArrayList<>();
    private boolean needAlert = false;

    public DeviceView(String ip) {
        this.ip = ip;
        for (int i = 0; i < 5; i++) {
            deviceStatuses.add(DeviceStatus.NO_DATA);
        }
    }

    public boolean isNeedAlert() {
        return needAlert;
    }

    public void setNeedAlert(boolean needAlert) {
        this.needAlert = needAlert;
    }

    public void setStatus(int statusPosition, DeviceStatus status) {
        deviceStatuses.add(statusPosition, status);
    }

    public String getStatus(int statusPosition) {
        return deviceStatuses.get(statusPosition).toString();
    }
}
