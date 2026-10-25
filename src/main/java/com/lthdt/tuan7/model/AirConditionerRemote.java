/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

package com.lthdt.tuan7.model;

import java.util.Set;

public class AirConditionerRemote extends TeachingDevice {
    private final Set<String> compatibleRooms;

    public AirConditionerRemote(String id, String name, String storageLocation, Set<String> dsPhong) {
        super(id, name, storageLocation);
        if (dsPhong == null) {
            throw new IllegalArgumentException("Danh sach phong khong hop le");
        }
        this.compatibleRooms = dsPhong;
    }

    public Set<String> getCompatibleRooms() {
        return compatibleRooms;
    }

    public boolean isCompatibleWith(String roomId) {
        return compatibleRooms.contains(roomId);
    }

    @Override
    public boolean isReadyForUse() {
        return getStatus() == DeviceStatus.AVAILABLE;
    }

    // diem khi da biet phong can dung
    public double tinhDiemTheoPhong(String roomId) {
        return isCompatibleWith(roomId) ? 100 : 0;
    }

    @Override
    public double getDefaultPriorityScore() {
        // TODO: chua co phong cu the thi tam tinh theo co phong tuong thich hay khong
        return compatibleRooms.isEmpty() ? 0 : 100;
    }
}
