package com.ceyeclon.monitoringapp.service.impl;

import com.ceyeclon.monitoringapp.model.DevicePingNote;
import com.ceyeclon.monitoringapp.service.CacheService;
import com.ceyeclon.monitoringapp.storage.Storage;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class InMemoryCacheService implements CacheService<String, DevicePingNote> {

    private Storage<String, DevicePingNote> storage;

    public InMemoryCacheService() {
    }

    @Inject
    public InMemoryCacheService(Storage<String, DevicePingNote> storage) {
        this.storage = storage;
    }

    @Override
    public void cache(DevicePingNote note) {
        storage.store(note);
    }

    @Override
    public List<DevicePingNote> fetchNLastEntries(String ip, int lastEntriesNumber) {
        return storage.fetchNLastEntries(ip, lastEntriesNumber);
    }

    @Override
    public List<String> getDevices() {
        List<String> keys = storage.getStoredKeys()
                .stream()
                .collect(Collectors.toList());
        return keys;
    }

    @Override
    public void archiveNotesOlderThan(long archiveTimeMinutes) {
        storage.archiveOlderThan(archiveTimeMinutes);
    }
}
