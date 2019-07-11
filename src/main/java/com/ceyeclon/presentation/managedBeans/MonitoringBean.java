package com.ceyeclon.presentation.managedBeans;

import com.ceyeclon.presentation.model.DeviceView;
import com.ceyeclon.presentation.service.ViewService;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("monitoring")
@ApplicationScoped
public class MonitoringBean {

    @Inject
    private ViewService viewService;

    List<DeviceView> allDeviceViews;

    public List<DeviceView> getAllDeviceViews() {
        allDeviceViews = viewService.getDeviceViews();
        return viewService.getDeviceViews();
    }

}
