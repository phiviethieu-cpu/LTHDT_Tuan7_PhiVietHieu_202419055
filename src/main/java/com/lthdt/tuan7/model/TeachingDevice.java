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
        this.id = kiemTraChuoi(id, "Ma thiet bi");
        this.name = kiemTraChuoi(name, "Ten thiet bi");
        this.storageLocation = kiemTraChuoi(storageLocation, "Vi tri luu tru");
        this.status = DeviceStatus.AVAILABLE;
    }

    // chuoi khong duoc null, rong hoac toan dau cach
    private static String kiemTraChuoi(String s, String tenTruong) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException(tenTruong + " khong duoc de trong");
        }
        return s;
    }

    // pin, tin hieu phai tu 0 den 100
    protected static int kiemTraPhanTram(int giaTri, String tenTruong) {
        if (giaTri < 0 || giaTri > 100) {
            throw new IllegalArgumentException(tenTruong + " phai tu 0 den 100: " + giaTri);
        }
        return giaTri;
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
