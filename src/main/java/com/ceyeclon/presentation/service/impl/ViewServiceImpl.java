package com.ceyeclon.presentation.service.impl;

import com.ceyeclon.monitoringapp.model.DevicePingNote;
import com.ceyeclon.monitoringapp.service.CacheService;
import com.ceyeclon.presentation.model.DeviceState;
import com.ceyeclon.presentation.model.DeviceStatus;
import com.ceyeclon.presentation.model.DeviceView;
import com.ceyeclon.presentation.service.ViewService;
import com.ceyeclon.presentation.util.IpToLongConverter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ViewServiceImpl implements ViewService {

    private final int STATUS_NUMBER_TO_VIEW = 5;
    private CacheService<String, DevicePingNote> cacheService;
    private IpToLongConverter ipToLongConverter;

    public ViewServiceImpl() {
    }

    @Inject
    public ViewServiceImpl(CacheService<String, DevicePingNote> cacheService, IpToLongConverter ipToLongConverter) {
        this.cacheService = cacheService;
        this.ipToLongConverter = ipToLongConverter;
    }

    @Override
    public List<DeviceView> getDeviceViews() {

        List<DeviceView> deviceViews = new ArrayList<>();
        List<String> devices = ipToLongConverter.sortIpList(cacheService.getDevices());

        for (String deviceIp : devices) {
            List<DevicePingNote> notes = cacheService.fetchNLastEntries(deviceIp, STATUS_NUMBER_TO_VIEW);
            DeviceView deviceView = new DeviceView(deviceIp);

            if (!notes.isEmpty()) {
                for (int i = 0; i < notes.size(); i++) {
                    if (notes.get(i).isOnline()) {
                        deviceView.setStatus(i, DeviceStatus.ONLINE);
                    } else {
                        deviceView.setStatus(i, DeviceStatus.OFFLINE);
                    }
                }
                deviceView.setLastPolled(secondsToLocalDateTime(
                        notes.get(notes.size() - 1).getTimeStampSeconds()));
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
            deviceViews.add(deviceView);
        }
        return deviceViews;
    }

    private LocalDateTime secondsToLocalDateTime(long seconds) {
        Instant instant = Instant.ofEpochSecond(seconds);
        LocalDateTime dateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return dateTime;
    }
}
