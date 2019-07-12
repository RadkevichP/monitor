package com.ceyeclon.presentation.managedBeans;

import com.ceyeclon.presentation.model.DeviceView;
import com.ceyeclon.presentation.service.ViewService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("monitoring")
@ViewScoped
public class MonitoringBean implements Serializable {

    @Inject
    private ViewService viewService;

    List<DeviceView> allDeviceViews;

    public List<DeviceView> getAllDeviceViews() {
        allDeviceViews = viewService.getDeviceViews();
        return viewService.getDeviceViews();
    }

}
