package com.lthdt.tuan7.model;

public class ProjectorRemote extends TeachingDevice {
    private final int batteryLevel;

    public ProjectorRemote(String id, String name, String storageLocation, int mucPin) {
        super(id, name, storageLocation);
        this.batteryLevel = mucPin;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    @Override
    public boolean isReadyForUse() {
        return getStatus() == DeviceStatus.AVAILABLE && batteryLevel >= 20;
    }

    @Override
    public double getDefaultPriorityScore() {
        return batteryLevel;
    }
}
