package com.ceyeclon.monitoringapp.model;

import java.util.Objects;

public class DevicePingNote {

    private String ip;
    private boolean isProcessed;
    private boolean isOnline;
    private long timeStampSeconds;

    public DevicePingNote(long timeStampSeconds) {
        this.timeStampSeconds = timeStampSeconds;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public long getTimeStampSeconds() {
        return timeStampSeconds;
    }

    public void setTimeStampSeconds(long timeStampSeconds) {
        this.timeStampSeconds = timeStampSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DevicePingNote that = (DevicePingNote) o;
        return isProcessed == that.isProcessed &&
                isOnline == that.isOnline &&
                timeStampSeconds == that.timeStampSeconds &&
                ip.equals(that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, isProcessed, isOnline, timeStampSeconds);
    }

    @Override
    public String toString() {
        return "DevicePingNote{" +
                "ip='" + ip + '\'' +
                ", isProcessed=" + isProcessed +
                ", isOnline=" + isOnline +
                ", timeStampSeconds=" + timeStampSeconds +
                '}';
    }
}
