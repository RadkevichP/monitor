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

    //TODO: take these values from property file
    private static final long POLLING_INITIAL_DELAY = 1;
    private static final long POLLING_INTERVAL = 3;
    private static final long INVESTIGATION_INITIAL_DELAY = 3;
    private static final long INVESTIGATION_INTERVAL = 1;
    private static final long ARCHIVE_TIME_MINUTES = 100;
    private static final long ARCHIVE_INTERVAL = 15;

    @Resource(lookup = "concurrent/__defaultManagedScheduledExecutorService")
    private ManagedScheduledExecutorService scheduler;

    @Resource(lookup = "concurrent/__defaultManagedThreadFactory")
    private ManagedThreadFactory threadFactory;

    private CacheService<String, DevicePingNote> cacheService;
    private PollService pollService;
    private InvestigationService investigationService;

    private ExecutorService normalPollingExecutor;
    private ExecutorService investigationPollingExecutor;
    private ExecutorService archiveExecutor;


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
        archiveExecutor = Executors.newFixedThreadPool(1, threadFactory);

        System.out.println("Monitor created!");

        scheduler.scheduleAtFixedRate(this::monitorDevices, POLLING_INITIAL_DELAY, POLLING_INTERVAL, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(this::investigateOfflineDevices, INVESTIGATION_INITIAL_DELAY,
                INVESTIGATION_INTERVAL, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(this::archiveCachedNotes, ARCHIVE_TIME_MINUTES, ARCHIVE_INTERVAL, TimeUnit.MINUTES);
    }


    @Override
    public void monitorDevices() {
        List<ToDevice> pingableDevices = pollService.findPingableDevices();
        List<Future<DevicePingNote>> futurePingNotes;
        try {
            futurePingNotes = normalPollingExecutor.invokeAll(createPollingTasks(pingableDevices));
            for (Future<DevicePingNote> pingNoteFuture : futurePingNotes) {

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

    @Override
    public void investigateOfflineDevices() {
        investigationPollingExecutor.submit(() -> {
            System.out.println("Investigation started!");
            Set<String> offlineDeviceIps = investigationService.getDevicesUnderInvestigation();
            if (!offlineDeviceIps.isEmpty()) {
                System.out.println("Some devices were offline");
                for (String offlineDeviceIp : offlineDeviceIps) {
                    ToDevice device = new ToDevice();
                    device.setIp(offlineDeviceIp);
                    DevicePingNote note = pollService.pollDevice(device);
                    if (note.isOnline()) {
                        investigationService.deleteFromInvestigation(note.getIp());
                    }
                    System.out.println(note.toString());
                    cacheService.cache(note);
                }
            } else return;
        });
    }

    public void archiveCachedNotes() {
        archiveExecutor.submit(() -> cacheService.archiveNotesOlderThan(ARCHIVE_TIME_MINUTES));
    }

    private List<Callable<DevicePingNote>> createPollingTasks(List<ToDevice> devices) {
        List<Callable<DevicePingNote>> pollingTasks = new ArrayList<>();
        for (ToDevice device : devices) {
            if (!investigationService.isUnderInvestigation(device)) {
                pollingTasks.add(() -> pollService.pollDevice(device));
            }
        }
        return pollingTasks;
    }

}
