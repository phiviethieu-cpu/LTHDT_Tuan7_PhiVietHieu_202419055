/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

package com.lthdt.tuan7.repository;

import com.lthdt.tuan7.exception.DuplicateIdException;
import com.lthdt.tuan7.model.Identifiable;
import com.lthdt.tuan7.model.ProjectorRemote;
import com.lthdt.tuan7.model.WirelessMicrophone;
import com.lthdt.tuan7.model.TeachingDevice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KhoLuuTruTest {
    private InMemoryRepository<ProjectorRemote> kho;

    @BeforeEach
    void setUp() {
        kho = new InMemoryRepository<>();
        kho.add(new ProjectorRemote("PR01", "May chieu A101", "Kho A", 90));
        kho.add(new ProjectorRemote("PR02", "May chieu A102", "Kho A", 15));
        kho.add(new ProjectorRemote("PR03", "May chieu A103", "Kho B", 60));
    }

    @Test
    void themVaTimTheoMa() {
        assertEquals(3, kho.getAll().size());
        assertEquals("May chieu A102", kho.findById("PR02").getName());
    }

    @Test
    void timMaKhongCoThiTraVeNull() {
        assertNull(kho.findById("XX99"));
    }

    @Test
    void themTrungMaThiBaoLoi() {
        DuplicateIdException ex = assertThrows(DuplicateIdException.class,
                () -> kho.add(new ProjectorRemote("PR01", "May chieu khac", "Kho C", 50)));
        assertEquals("PR01", ex.getDuplicateId());
        assertEquals(3, kho.getAll().size());
    }

    @Test
    void khongChoThemNull() {
        assertThrows(IllegalArgumentException.class, () -> kho.add(null));
    }

    @Test
    void xoaTheoMa() {
        assertTrue(kho.remove("PR03"));
        assertFalse(kho.remove("PR03"));
        assertNull(kho.findById("PR03"));
    }

    @Test
    void getAllKhongSuaDuocTuBenNgoai() {
        List<ProjectorRemote> ds = kho.getAll();
        assertThrows(UnsupportedOperationException.class, ds::clear);
        assertEquals(3, kho.getAll().size());
    }

    @Test
    void getAllGiuThuTuThem() {
        List<ProjectorRemote> ds = kho.getAll();
        assertEquals("PR01", ds.get(0).getId());
        assertEquals("PR03", ds.get(2).getId());
    }

    @Test
    void locThietBiSanSang() {
        List<ProjectorRemote> sanSang = kho.findAll(TeachingDevice::isReadyForUse);

        assertEquals(2, sanSang.size());
        assertTrue(sanSang.stream().noneMatch(p -> p.getId().equals("PR02")));
    }

    // cung 1 lop kho dung cho loai thiet bi khac
    @Test
    void dungChoMicro() {
        InMemoryRepository<WirelessMicrophone> khoMic = new InMemoryRepository<>();
        khoMic.add(new WirelessMicrophone("MC01", "Micro 1", "Kho B", 80, 70));
        khoMic.add(new WirelessMicrophone("MC02", "Micro 2", "Kho B", 20, 90));

        assertEquals(1, khoMic.findAll(m -> m.isReadyForUse()).size());
        assertEquals(90, khoMic.findById("MC02").getSignalQuality());
    }

    @Test
    void findAllDieuKienNullThiBaoLoi() {
        assertThrows(IllegalArgumentException.class, () -> kho.findAll(null));
    }

    // kho generic co the chua doi tuong khong phai thiet bi
    @Test
    void themDoiTuongMaRongThiBaoLoi() {
        InMemoryRepository<Identifiable> khoChung = new InMemoryRepository<>();
        assertThrows(IllegalArgumentException.class, () -> khoChung.add(() -> "   "));
        assertThrows(IllegalArgumentException.class, () -> khoChung.add(() -> null));
    }
}
