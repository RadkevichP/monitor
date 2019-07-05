package com.ceyeclon.monitoringapp.monitor.impl;

import com.ceyeclon.monitoringapp.model.DevicePingNote;
import com.ceyeclon.monitoringapp.monitor.Monitor;
import com.ceyeclon.monitoringapp.util.Converter;
import generated.MLZStateAPI;
import generated.MLZStateAPI_Service;
import generated.PingTO;
import generated.ToDevice;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Startup
public class CeyeclonMonitor implements Monitor {

    private final String apiKey = "lnzihhej8h80zu9q75n6lt7bw1ed6qefcrwdystscanez0ifz3zjygzvionupdromrk07n2k2znvd484vi2qwd8vp9bnazq1p75h1iqg7tp509xjvjih6wp2dh64rcb2vebnrehvzk3ekgmkf2h3l5";
    private int gateway = 1;

    @Inject
    private MLZStateAPI_Service mlzStateAPI_service; /*= new MLZStateAPI_Service();*/

    @Inject
    private Converter<PingTO, DevicePingNote> converter;
    private MLZStateAPI mlzStateAPI;


    @PostConstruct
    public void initialize() {
        mlzStateAPI = mlzStateAPI_service.getMLZStateAPIPort();
        System.out.println("Monitor created!");
    }


    @Override
    public String monitorDevices() {
        List<ToDevice> devices = this.findPingableDevices();
        String poll = converter.convert(
                mlzStateAPI.ping(devices.get(0).getIp(), gateway))
                .toString();
        return poll;
    }

    public List<ToDevice> findPingableDevices() {
        List<ToDevice> pingableDevices = mlzStateAPI.getDeviceList(apiKey)
                .stream()
                .filter(device -> (device.getIp() != null))
                .collect(Collectors.toList());
        return pingableDevices;
    }
}
