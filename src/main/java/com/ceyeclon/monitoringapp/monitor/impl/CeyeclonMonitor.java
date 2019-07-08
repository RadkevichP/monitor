package com.ceyeclon.monitoringapp.monitor.impl;

import com.ceyeclon.monitoringapp.model.DevicePingNote;
import com.ceyeclon.monitoringapp.monitor.Monitor;
import com.ceyeclon.monitoringapp.service.CacheService;
import com.ceyeclon.monitoringapp.service.InvestigationService;
import com.ceyeclon.monitoringapp.service.PollService;
import generated.ToDevice;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

@Singleton
@Startup
public class CeyeclonMonitor implements Monitor {

    //TODO: take this value from property file
    private static final long INITIAL_DELAY = 1;
    private static final long PERIOD = 3;
    private static final long INVESTIGATION_INITIAL_DELAY = 3;
    private static final long INVESTIGATION_INTERVAL = 1;

    @Resource(lookup = "concurrent/__defaultManagedScheduledExecutorService")
    private ManagedScheduledExecutorService scheduler;

    @Resource(lookup = "concurrent/__defaultManagedThreadFactory")
    private ManagedThreadFactory threadFactory;

    private CacheService<String, DevicePingNote> cacheService;
    private PollService pollService;
    private InvestigationService investigationService;
    private ExecutorService normalPollingExecutor;
    private ExecutorService investigationPollingExecutor;

    public CeyeclonMonitor() {
    }

    @Inject
    public CeyeclonMonitor(CacheService<String, DevicePingNote> cacheService, PollService pollService,
                           InvestigationService investigationService) {
        this.cacheService = cacheService;
        this.pollService = pollService;
        this.investigationService = investigationService;
    }

    @PostConstruct
    public void initialize() {
        normalPollingExecutor = Executors.newFixedThreadPool(3, threadFactory);
        investigationPollingExecutor = Executors.newFixedThreadPool(1, threadFactory);
        System.out.println("Monitor created!");
        scheduler.scheduleAtFixedRate(this::monitorDevices, INITIAL_DELAY, PERIOD, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(this::investigateOfflineDevices, INVESTIGATION_INITIAL_DELAY,
                INVESTIGATION_INTERVAL, TimeUnit.MINUTES);
    }


    @Override
    public void monitorDevices() {
        List<ToDevice> pingableDevices = pollService.findPingableDevices();
        List<Future<DevicePingNote>> futures;
        try {
            futures = normalPollingExecutor.invokeAll(createPollingTasks(pingableDevices));
            for (Future<DevicePingNote> pingNoteFuture : futures) {
                System.out.println(pingNoteFuture.get().toString());
                DevicePingNote note = pingNoteFuture.get();
                if (!note.isOnline()) {
                    investigationService.addToInvestigation(note.getIp());
                }
                cacheService.cache(note);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void investigateOfflineDevices() {
        investigationPollingExecutor.submit(() -> {
            System.out.println("Started investigation!");
            Set<String> offlineDeviceIps = investigationService.getDevicesUnderInvestigation();
            if (!offlineDeviceIps.isEmpty()) {
                System.out.println("Some device was offline");
                for (String offlineDeviceIp : offlineDeviceIps) {
                    ToDevice device = new ToDevice();
                    device.setIp(offlineDeviceIp);
                    DevicePingNote note = pollService.pollDevice(device);
                    if (note.isOnline()) {
                        investigationService.deleteFromInvestigation(note.getIp());
                    }
                    cacheService.cache(note);
                }
            } else return;
        });
    }

    private List<Callable<DevicePingNote>> createPollingTasks(List<ToDevice> devices) {
        List<Callable<DevicePingNote>> pollingTasks = new ArrayList<>();
        for (ToDevice device : devices) {
            if (!investigationService.isUnderInvestigation(device)) {
                pollingTasks.add(() -> {
                    System.out.println("Hi there!");
                    return pollService.pollDevice(device);
                });
            }
        }
        return pollingTasks;
    }


}
