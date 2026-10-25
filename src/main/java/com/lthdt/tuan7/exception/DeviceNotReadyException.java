/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

package com.lthdt.tuan7.exception;

import com.lthdt.tuan7.model.DeviceStatus;

// muon thiet bi khi chua san sang
public class DeviceNotReadyException extends RuntimeException {
    private final String deviceId;
    private final DeviceStatus currentStatus;

    public DeviceNotReadyException(String deviceId, DeviceStatus currentStatus) {
        super("Thiet bi " + deviceId + " chua san sang, trang thai hien tai: " + currentStatus);
        this.deviceId = deviceId;
        this.currentStatus = currentStatus;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public DeviceStatus getCurrentStatus() {
        return currentStatus;
    }
}
