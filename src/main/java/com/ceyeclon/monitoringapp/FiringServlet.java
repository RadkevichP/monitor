package com.ceyeclon.monitoringapp;

import com.ceyeclon.monitoringapp.monitor.Monitor;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/start")
public class FiringServlet extends HttpServlet {

    @Inject
    private Monitor monitor;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //monitor.monitorDevices();
    }
}
