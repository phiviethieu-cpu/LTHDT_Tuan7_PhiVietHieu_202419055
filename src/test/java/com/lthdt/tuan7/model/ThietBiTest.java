/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

package com.lthdt.tuan7.model;

import com.lthdt.tuan7.exception.DeviceNotReadyException;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ThietBiTest {

    @Test
    void mayChieuDuPinThiSanSang() {
        assertTrue(new ProjectorRemote("PR01", "Dieu khien may chieu", "Kho A", 20).isReadyForUse());
        assertFalse(new ProjectorRemote("PR02", "Dieu khien may chieu", "Kho A", 19).isReadyForUse());
    }

    @Test
    void mayChieuDiemBangMucPin() {
        ProjectorRemote pr = new ProjectorRemote("PR01", "Dieu khien may chieu", "Kho A", 75);
        assertEquals(75.0, pr.getDefaultPriorityScore());
    }

    @Test
    void micCanDuPinVaTinHieu() {
        assertTrue(new WirelessMicrophone("MC01", "Micro", "Kho B", 30, 40).isReadyForUse());
        assertFalse(new WirelessMicrophone("MC02", "Micro", "Kho B", 29, 90).isReadyForUse());
        assertFalse(new WirelessMicrophone("MC03", "Micro", "Kho B", 90, 39).isReadyForUse());
    }

    @Test
    void micDiemLaTrungBinh() {
        WirelessMicrophone mic = new WirelessMicrophone("MC01", "Micro", "Kho B", 80, 60);
        assertEquals(70.0, mic.getDefaultPriorityScore());
    }

    @Test
    void dieuHoaKiemTraPhong() {
        AirConditionerRemote ac = new AirConditionerRemote("AC01", "Dieu khien dieu hoa", "Kho A",
                Set.of("A203", "A204"));

        assertTrue(ac.isCompatibleWith("A203"));
        assertFalse(ac.isCompatibleWith("B101"));
        assertEquals(100.0, ac.tinhDiemTheoPhong("A204"));
        assertEquals(0.0, ac.tinhDiemTheoPhong("B101"));
        assertTrue(ac.isReadyForUse());
    }

    @Test
    void trangThaiBanDauLaAvailable() {
        ProjectorRemote pr = new ProjectorRemote("PR01", "Dieu khien may chieu", "Kho A", 50);
        assertEquals(DeviceStatus.AVAILABLE, pr.getStatus());
    }

    @Test
    void idNullThiBaoLoi() {
        assertThrows(IllegalArgumentException.class,
                () -> new ProjectorRemote(null, "Dieu khien may chieu", "Kho A", 50));
    }

    // muon / tra
    @Test
    void muonXongChuyenSangBorrowed() {
        WirelessMicrophone mic = new WirelessMicrophone("MC01", "Micro", "Kho B", 80, 60);
        mic.borrow();

        assertEquals(DeviceStatus.BORROWED, mic.getStatus());
        assertFalse(mic.isReadyForUse());
    }

    @Test
    void muonKhiYeuPinThiBaoLoi() {
        ProjectorRemote pr = new ProjectorRemote("PR03", "Dieu khien may chieu", "Kho A", 10);

        DeviceNotReadyException ex = assertThrows(DeviceNotReadyException.class, pr::borrow);
        assertEquals("PR03", ex.getDeviceId());
        assertEquals(DeviceStatus.AVAILABLE, ex.getCurrentStatus());
    }

    @Test
    void khongMuonDuocHaiLan() {
        ProjectorRemote pr = new ProjectorRemote("PR01", "Dieu khien may chieu", "Kho A", 90);
        pr.borrow();

        DeviceNotReadyException ex = assertThrows(DeviceNotReadyException.class, pr::borrow);
        assertEquals(DeviceStatus.BORROWED, ex.getCurrentStatus());
    }

    @Test
    void traThietBiVeAvailable() {
        AirConditionerRemote ac = new AirConditionerRemote("AC01", "Dieu khien dieu hoa", "Kho A", Set.of("A203"));
        ac.borrow();
        ac.returnDevice();

        assertEquals(DeviceStatus.AVAILABLE, ac.getStatus());
    }

    @Test
    void traKhiChuaMuonThiBaoLoi() {
        ProjectorRemote pr = new ProjectorRemote("PR01", "Dieu khien may chieu", "Kho A", 90);
        assertThrows(IllegalStateException.class, pr::returnDevice);
    }
}
