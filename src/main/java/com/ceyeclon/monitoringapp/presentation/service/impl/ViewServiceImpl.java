package com.ceyeclon.monitoringapp.presentation.service.impl;

import com.ceyeclon.monitoringapp.model.DevicePingNote;
import com.ceyeclon.monitoringapp.presentation.model.DeviceState;
import com.ceyeclon.monitoringapp.presentation.model.DeviceStatus;
import com.ceyeclon.monitoringapp.presentation.model.DeviceView;
import com.ceyeclon.monitoringapp.presentation.service.ViewService;
import com.ceyeclon.monitoringapp.service.CacheService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ViewServiceImpl implements ViewService {

    private CacheService<String, DevicePingNote> cacheService;

    public ViewServiceImpl() {
    }

    @Inject
    public ViewServiceImpl(CacheService<String, DevicePingNote> cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public List<DeviceView> getDeviceViews() {
        List<DeviceView> deviceViews = new ArrayList<>();
        List<String> devices = cacheService.getDevices();
        for (String deviceIp : devices) {
            List<DevicePingNote> notes = cacheService.fetchNLastEntries(deviceIp, 5);
            DeviceView deviceView = new DeviceView(deviceIp);
            if (!notes.isEmpty()) {
                for (int i = 0; i < notes.size(); i++) {
                    if (notes.get(i).isOnline()) {
                        deviceView.setStatus(i, DeviceStatus.ONLINE);
                    } else {
                        deviceView.setStatus(i, DeviceStatus.OFFLINE);
                    }
                }
            }
            List<DeviceStatus> deviceStatuses = deviceView.getDeviceStatuses();
            if (!deviceStatuses.contains(DeviceStatus.NO_DATA) && !deviceStatuses.contains(DeviceStatus.ONLINE)) {
                deviceView.setDeviceState(DeviceState.OFFLINE);
            } else {
                if (deviceStatuses.get(0).equals(DeviceStatus.OFFLINE)) {
                    deviceView.setDeviceState(DeviceState.UNDER_INVESTIGATION);
                } else {
                    deviceView.setDeviceState(DeviceState.ONLINE);
                }
            }
            System.out.println(deviceView.toString());
            deviceViews.add(deviceView);
        }
        return deviceViews;
    }
}
