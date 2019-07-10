package com.ceyeclon.monitoringapp.presentation;

import com.ceyeclon.monitoringapp.presentation.model.DeviceView;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("dummy")
@RequestScoped
public class DummyBean implements Serializable {


    @Inject
    ViewService viewService;

    String contract = "Pavel Radkevich";

    List<DeviceView> allDeviceViews;

    public List<DeviceView> getAllDeviceViews() {
        allDeviceViews = viewService.getDeviceViews();
        return allDeviceViews;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

}
