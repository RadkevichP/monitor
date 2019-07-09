package com.ceyeclon.monitoringapp.storage.impl;

import com.ceyeclon.monitoringapp.model.DevicePingNote;
import com.ceyeclon.monitoringapp.storage.Storage;

import javax.ejb.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class InMemoryStorage implements Storage<String, DevicePingNote> {

    private Map<String, List<DevicePingNote>> storage = new HashMap<>();

    @Override
    @Lock(LockType.WRITE)
    public void store(DevicePingNote devicePingNote) {
        String deviceIp = devicePingNote.getIp();
        if (!storage.containsKey(deviceIp)) {
            storage.put(deviceIp, new ArrayList<DevicePingNote>() {{
                add(devicePingNote);
            }});
        } else {
            storage.get(deviceIp).add(devicePingNote);
        }
    }

    @Override
    @Lock(LockType.READ)
    public List<DevicePingNote> fetchAllEntries(String deviceIp) {
        if (storage.containsKey(deviceIp)) {
            return storage.get(deviceIp);
        } else throw new RuntimeException("Device not found!");
    }

    @Override
    @Lock(LockType.READ)
    public List<DevicePingNote> fetchNLastEntries(String deviceIp, int lastEntriesNumber) {
        if (storage.containsKey(deviceIp)) {
            List<DevicePingNote> notes = storage.get(deviceIp);
            int notesNumber = notes.size();

            if (notesNumber <= lastEntriesNumber) {
                return notes;
            } else {
                return notes.subList((notesNumber - 1 - lastEntriesNumber), (notesNumber - 1));
            }
        } else throw new RuntimeException("Device not found!");
    }

    @Override
    @Lock(LockType.READ)
    public Set<String> getStoredKeys() {
        return storage.keySet();
    }

    @Override
    @Lock(LockType.WRITE)
    public void archiveOlderThan(long minutes) {
        for (Map.Entry<String, List<DevicePingNote>> storageEntry : storage.entrySet()) {
            List<DevicePingNote> actualNotes = storageEntry.getValue()
                    .stream()
                    .filter(note -> (note.getTimeStampSeconds() > (Instant.now().getEpochSecond() - (minutes * 60))))
                    .collect(Collectors.toList());
            if (!actualNotes.isEmpty()) {
                storageEntry.setValue(actualNotes);
                System.out.println("Notes actualized!");
            } else storage.remove(storageEntry.getKey());
        }
    }
}
