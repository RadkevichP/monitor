package com.ceyeclon.monitoringapp.presentation;

import com.ceyeclon.monitoringapp.model.DevicePingNote;
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
        for (String deviceIp: devices) {
            List<DevicePingNote> notes = cacheService.fetchNLastEntries(deviceIp, 5);
            DeviceView deviceView = new DeviceView(deviceIp);
            if (!notes.isEmpty()) {
                for (int i = 0; i < notes.size(); i++) {

                }
            }
        }


        return null;
    }
}
