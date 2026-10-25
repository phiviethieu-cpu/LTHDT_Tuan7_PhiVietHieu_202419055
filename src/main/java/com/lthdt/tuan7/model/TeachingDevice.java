/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

package com.lthdt.tuan7.model;

import com.lthdt.tuan7.exception.DeviceNotReadyException;

public abstract class TeachingDevice implements Identifiable {
    private final String id;
    private final String name;
    private final String storageLocation;
    private DeviceStatus status;

    protected TeachingDevice(String id, String name, String storageLocation) {
        if (id == null || name == null || storageLocation == null) {
            throw new IllegalArgumentException("Thong tin thiet bi khong duoc de trong");
        }
        this.id = id;
        this.name = name;
        this.storageLocation = storageLocation;
        this.status = DeviceStatus.AVAILABLE;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    protected void setStatus(DeviceStatus status) {
        this.status = status;
    }

    // moi loai thiet bi tu cai dat
    public abstract boolean isReadyForUse();

    public abstract double getDefaultPriorityScore();

    // cho muon thiet bi
    public void borrow() {
        if (!isReadyForUse()) {
            throw new DeviceNotReadyException(id, status);
        }
        status = DeviceStatus.BORROWED;
    }

    // tra thiet bi
    public void returnDevice() {
        if (status != DeviceStatus.BORROWED) {
            throw new IllegalStateException("Thiet bi " + id + " chua duoc muon");
        }
        status = DeviceStatus.AVAILABLE;
    }
}
