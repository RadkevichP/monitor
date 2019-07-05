package com.ceyeclon.monitoringapp.service.impl;

import com.ceyeclon.monitoringapp.model.DevicePingNote;
import com.ceyeclon.monitoringapp.service.PollService;
import com.ceyeclon.monitoringapp.util.Converter;
import generated.MLZStateAPI;
import generated.MLZStateAPI_Service;
import generated.PingTO;
import generated.ToDevice;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CeyeclonPollService implements PollService {

    private MLZStateAPI_Service mlzStateAPI_service;
    private MLZStateAPI mlzStateAPI;
    private Converter<PingTO, DevicePingNote> converter;

    //TODO: take this value from property file
    private final String apiKey = "lnzihhej8h80zu9q75n6lt7bw1ed6qefcrwdystscanez0ifz3zjygzvionupdromrk07n2k2znvd484vi2qwd8vp9bnazq1p75h1iqg7tp509xjvjih6wp2dh64rcb2vebnrehvzk3ekgmkf2h3l5";
    private final int gateway = 1;

    public CeyeclonPollService() {
    }

    @Inject
    public CeyeclonPollService(MLZStateAPI_Service mlzStateAPI_service, Converter<PingTO, DevicePingNote> converter) {
        this.mlzStateAPI_service = mlzStateAPI_service;
        this.converter = converter;
    }

    @PostConstruct
    public void initialize() {
        mlzStateAPI = mlzStateAPI_service.getMLZStateAPIPort();
    }

    @Override
    public DevicePingNote pollDevice(ToDevice device) {
        return converter.convert(mlzStateAPI.ping(device.getIp(), gateway));
    }

    @Override
    public List<ToDevice> findPingableDevices() {
        List<ToDevice> pingableDevices = mlzStateAPI.getDeviceList(apiKey)
                .stream()
                .filter(device -> (device.getIp() != null))
                .collect(Collectors.toList());
        return pingableDevices;
    }
}
