package com.ceyeclon.monitoringapp.monitor.impl;

import com.ceyeclon.monitoringapp.model.DevicePingNote;
import com.ceyeclon.monitoringapp.monitor.Monitor;
import com.ceyeclon.monitoringapp.service.CacheService;
import com.ceyeclon.monitoringapp.service.PollService;
import generated.ToDevice;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.List;

@Singleton
@Startup
public class CeyeclonMonitor implements Monitor {

    static final long INITIAL_DELAY = 5;
    static final long PERIOD = 3;


    private CacheService<String, DevicePingNote> cacheService;
    private PollService pollService;

    public CeyeclonMonitor() {
    }

    @Inject
    public CeyeclonMonitor(CacheService<String, DevicePingNote> cacheService, PollService pollService) {
        this.cacheService = cacheService;
        this.pollService = pollService;
    }

    @PostConstruct
    public void initialize() {
        System.out.println("Monitor created!");
    }


    @Override
    @Schedule(minute = "*/3",
            hour = "*",
            info = "PollingScheduler",
            persistent = false)
    public void monitorDevices() {
        List<ToDevice> devices = pollService.findPingableDevices();
        for (ToDevice device : devices) {
            DevicePingNote note = pollService.pollDevice(device);
            System.out.println(note.toString());
            cacheService.cache(note);

        }
    }

}
