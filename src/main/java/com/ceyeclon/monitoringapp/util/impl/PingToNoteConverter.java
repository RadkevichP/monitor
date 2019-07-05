package com.ceyeclon.monitoringapp.util.impl;

import com.ceyeclon.monitoringapp.model.DevicePingNote;
import com.ceyeclon.monitoringapp.util.Converter;
import generated.PingTO;

import javax.ejb.Stateless;
import java.time.Instant;

@Stateless
public class PingToNoteConverter implements Converter<PingTO, DevicePingNote> {

    @Override
    public DevicePingNote convert(PingTO pingTO) {
        if (pingTO.getIp() != null) {
            DevicePingNote pingNote = new DevicePingNote(Instant.now().getEpochSecond());
            pingNote.setIp(pingTO.getIp());
            pingNote.setProcessed(pingTO.isProcessed());
            pingNote.setOnline(pingTO.isState());
            return pingNote;
        } else {
            throw new RuntimeException("Device IP must not be null!");
        }
    }
}
