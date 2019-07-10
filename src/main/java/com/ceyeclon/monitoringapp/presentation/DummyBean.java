package com.ceyeclon.monitoringapp.presentation;

import com.ceyeclon.monitoringapp.model.DevicePingNote;
import com.ceyeclon.monitoringapp.service.CacheService;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("dummy")
@RequestScoped
public class DummyBean implements Serializable {


    @Inject
    CacheService<String, DevicePingNote> cacheService;

    String contract = "Pavel Radkevich";

    List<String> allDevices;

    public List<String> getAllDevices() {
        return cacheService.getDevices();
    }

    public void setAllDevices(List<String> allDevices) {
        this.allDevices = allDevices;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

}
