/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

package com.lthdt.tuan7.model;

public class WirelessMicrophone extends TeachingDevice {
    private final int batteryLevel;
    private final int signalQuality;

    public WirelessMicrophone(String id, String name, String storageLocation, int mucPin, int tinHieu) {
        super(id, name, storageLocation);
        this.batteryLevel = kiemTraPhanTram(mucPin, "Muc pin");
        this.signalQuality = kiemTraPhanTram(tinHieu, "Chat luong tin hieu");
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
        return (batteryLevel + signalQuality) / 2.0;
    }
}
