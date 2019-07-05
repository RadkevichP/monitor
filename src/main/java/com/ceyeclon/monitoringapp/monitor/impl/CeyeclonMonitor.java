package com.ceyeclon.monitoringapp.monitor.impl;

import com.ceyeclon.monitoringapp.model.DevicePingNote;
import com.ceyeclon.monitoringapp.monitor.Monitor;
import com.ceyeclon.monitoringapp.service.CacheService;
import com.ceyeclon.monitoringapp.service.PollService;
import generated.ToDevice;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Singleton
@Startup
public class CeyeclonMonitor implements Monitor {

    //TODO: take this value from property file
    private static final long INITIAL_DELAY = 1;
    private static final long PERIOD = 3;

    @Resource(lookup = "concurrent/__defaultManagedScheduledExecutorService")
    private ManagedScheduledExecutorService scheduler;


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
        this.scheduler.scheduleAtFixedRate(this::monitorDevices, INITIAL_DELAY, PERIOD, TimeUnit.MINUTES);
    }


    @Override
    public void monitorDevices() {
        List<ToDevice> devices = pollService.findPingableDevices();
        for (ToDevice device : devices) {
            DevicePingNote note = pollService.pollDevice(device);
            System.out.println(note.toString());
            cacheService.cache(note);
        }
    }

}
