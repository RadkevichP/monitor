package com.ceyeclon.monitoringapp.storage.impl;

import com.ceyeclon.monitoringapp.model.DevicePingNote;
import com.ceyeclon.monitoringapp.storage.Storage;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryStorageTest {


    @Test
    void store() {

        Storage<String, DevicePingNote> storage = new InMemoryStorage();

        DevicePingNote note = new DevicePingNote(Instant.now().getEpochSecond());
        note.setIp("testIp");
        note.setProcessed(true);
        note.setOnline(false);
        storage.store(note);

        DevicePingNote noteFromStorage = storage.fetchNLastEntries(note.getIp(), 1).get(0);
        assertEquals(note, noteFromStorage);

    }

    @Test
    void fetchAllEntries() {

    }

    @Test
    void fetchNLastEntries() {

    }

    @Test
    void getStoredKeys() {

    }

}