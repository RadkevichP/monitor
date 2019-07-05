package com.ceyeclon.monitoringapp;

import com.ceyeclon.monitoringapp.model.DevicePingNote;
import com.ceyeclon.monitoringapp.service.CacheService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/test")
public class DummyWebService extends HttpServlet {


    @Inject
    private CacheService<String, DevicePingNote> cacheService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> devices = (cacheService.getDevices().
                stream().collect(Collectors.toList()));
        List<DevicePingNote> notes = cacheService.fetchNLastEntries(devices.get(2), 4);
        resp.getWriter().println("Hi form poller!" + Arrays.toString(notes.toArray()));
    }



}
