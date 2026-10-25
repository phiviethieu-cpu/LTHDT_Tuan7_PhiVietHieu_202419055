package com.lthdt.tuan7.service;

import com.lthdt.tuan7.exception.NoSuitableItemException;
import com.lthdt.tuan7.model.*;
import com.lthdt.tuan7.repository.InMemoryRepository;
import com.lthdt.tuan7.strategy.FirstAvailableStrategy;
import com.lthdt.tuan7.strategy.HighestPriorityStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CapPhatTest {
    private InMemoryRepository<ProjectorRemote> khoMayChieu;
    private InMemoryRepository<AirConditionerRemote> khoDieuHoa;
    private InMemoryRepository<WirelessMicrophone> khoMic;

    @BeforeEach
    void setUp() {
        khoMayChieu = new InMemoryRepository<>();
        khoMayChieu.add(new ProjectorRemote("PR01", "May chieu A101", "Kho A", 90));
        khoMayChieu.add(new ProjectorRemote("PR02", "May chieu A102", "Kho A", 15));
        khoMayChieu.add(new ProjectorRemote("PR03", "May chieu A103", "Kho B", 60));

        khoDieuHoa = new InMemoryRepository<>();
        khoDieuHoa.add(new AirConditionerRemote("AC01", "Dieu hoa tang 2", "Kho A", Set.of("A201", "A202")));
        khoDieuHoa.add(new AirConditionerRemote("AC02", "Dieu hoa tang 2", "Kho A", Set.of("A203", "A204")));

        khoMic = new InMemoryRepository<>();
        khoMic.add(new WirelessMicrophone("MC01", "Micro 1", "Kho B", 80, 70));
        khoMic.add(new WirelessMicrophone("MC02", "Micro 2", "Kho B", 20, 90));
        khoMic.add(new WirelessMicrophone("MC03", "Micro 3", "Kho C", 95, 85));
    }

    @Test
    void chonMayChieuDiemCaoNhat() {
        AllocationService<ProjectorRemote> service = new AllocationService<>(khoMayChieu,
                new HighestPriorityStrategy<>(ProjectorRemote::getDefaultPriorityScore));

        ProjectorRemote chon = service.allocate(d -> true);

        assertEquals("PR01", chon.getId());
        assertEquals(DeviceStatus.BORROWED, chon.getStatus());
    }

    @Test
    void capPhatLienTiepDenKhiHet() {
        AllocationService<ProjectorRemote> service = new AllocationService<>(khoMayChieu,
                new HighestPriorityStrategy<>(ProjectorRemote::getDefaultPriorityScore));

        assertEquals("PR01", service.allocate(d -> true).getId());
        assertEquals("PR03", service.allocate(d -> true).getId());
        // PR02 chi con 15% pin
        assertThrows(NoSuitableItemException.class, () -> service.allocate(d -> true));
    }

    @Test
    void chonDieuHoaTheoPhong() {
        AllocationService<AirConditionerRemote> service =
                new AllocationService<>(khoDieuHoa, new FirstAvailableStrategy<>());

        AirConditionerRemote chon = service.allocate(d -> d.isCompatibleWith("A203"));

        assertEquals("AC02", chon.getId());
        assertEquals(DeviceStatus.AVAILABLE, khoDieuHoa.findById("AC01").getStatus());
    }

    @Test
    void chonMicDauTienSanSang() {
        AllocationService<WirelessMicrophone> service =
                new AllocationService<>(khoMic, new FirstAvailableStrategy<>());

        assertEquals("MC01", service.allocate(d -> true).getId());
    }

    @Test
    void doiChienLuocKhiDangChay() {
        AllocationService<WirelessMicrophone> service =
                new AllocationService<>(khoMic, new FirstAvailableStrategy<>());

        WirelessMicrophone lan1 = service.allocate(d -> true);
        lan1.returnDevice();

        service.changeStrategy(new HighestPriorityStrategy<>(WirelessMicrophone::getDefaultPriorityScore));
        WirelessMicrophone lan2 = service.allocate(d -> true);

        assertEquals("MC01", lan1.getId());
        assertEquals("MC03", lan2.getId());
    }

    @Test
    void khongCoUngVienPhuHop() {
        AllocationService<AirConditionerRemote> service =
                new AllocationService<>(khoDieuHoa, new FirstAvailableStrategy<>());

        assertThrows(NoSuitableItemException.class, () -> service.allocate(d -> d.isCompatibleWith("B101")));
        assertEquals(DeviceStatus.AVAILABLE, khoDieuHoa.findById("AC01").getStatus());
        assertEquals(DeviceStatus.AVAILABLE, khoDieuHoa.findById("AC02").getStatus());
    }

    @Test
    void thietBiTrongKhoChuyenSangBorrowed() {
        AllocationService<WirelessMicrophone> service = new AllocationService<>(khoMic,
                new HighestPriorityStrategy<>(WirelessMicrophone::getDefaultPriorityScore));
        service.allocate(d -> d.getStorageLocation().equals("Kho C"));

        assertEquals(DeviceStatus.BORROWED, khoMic.findById("MC03").getStatus());
        assertEquals(DeviceStatus.AVAILABLE, khoMic.findById("MC01").getStatus());
    }

    @Test
    void khoiTaoThieuThamSoThiBaoLoi() {
        assertThrows(IllegalArgumentException.class,
                () -> new AllocationService<ProjectorRemote>(null, new FirstAvailableStrategy<>()));
        assertThrows(IllegalArgumentException.class,
                () -> new AllocationService<>(khoMayChieu, null));
    }
}
