package com.lthdt.tuan7.model;

public class WirelessMicrophone extends TeachingDevice {
    private final int batteryLevel;
    private final int signalQuality;

    public WirelessMicrophone(String id, String name, String storageLocation, int mucPin, int tinHieu) {
        super(id, name, storageLocation);
        this.batteryLevel = mucPin;
        this.signalQuality = tinHieu;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public int getSignalQuality() {
        return signalQuality;
    }

    @Override
    public boolean isReadyForUse() {
        return getStatus() == DeviceStatus.AVAILABLE
                && batteryLevel >= 30
                && signalQuality >= 40;
    }

    // trung binh pin va tin hieu
    @Override
    public double getDefaultPriorityScore() {
        return (batteryLevel + signalQuality) / 2;
    }
}
