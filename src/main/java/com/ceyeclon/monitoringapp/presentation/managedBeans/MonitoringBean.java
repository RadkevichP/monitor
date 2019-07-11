package com.ceyeclon.monitoringapp.presentation.managedBeans;

import com.ceyeclon.monitoringapp.presentation.model.DeviceView;
import com.ceyeclon.monitoringapp.presentation.service.ViewService;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("monitoring")
@RequestScoped
public class MonitoringBean {

    @Inject
    private ViewService viewService;

    List<DeviceView> allDeviceViews;

    public List<DeviceView> getAllDeviceViews() {
        allDeviceViews = viewService.getDeviceViews();
        return viewService.getDeviceViews();
    }

}
