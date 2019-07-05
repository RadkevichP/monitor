package com.ceyeclon.monitoringapp.util.impl;

import com.ceyeclon.monitoringapp.model.DevicePingNote;
import com.ceyeclon.monitoringapp.util.Converter;
import generated.PingTO;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class PingToNoteConverterTest {

    Converter<PingTO, DevicePingNote> converter = new PingToNoteConverter();

    @Test
    void convert() {

        PingTO ping = new PingTO();

        ping.setIp("testIp");
        ping.setProcessed(true);
        ping.setState(true);

        DevicePingNote note = converter.convert(ping);

        assertEquals(ping.getIp(), note.getIp());
        assertEquals(ping.isProcessed(), note.isProcessed());
        assertEquals(ping.isState(), note.isOnline());
        assertTrue(note.getTimeStampSeconds() <= Instant.now().getEpochSecond());

    }
}