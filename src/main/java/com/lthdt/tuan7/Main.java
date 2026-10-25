/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

package com.lthdt.tuan7;

import com.lthdt.tuan7.exception.DuplicateIdException;
import com.lthdt.tuan7.exception.NoSuitableItemException;
import com.lthdt.tuan7.model.*;
import com.lthdt.tuan7.repository.InMemoryRepository;
import com.lthdt.tuan7.service.AllocationService;
import com.lthdt.tuan7.strategy.FirstAvailableStrategy;
import com.lthdt.tuan7.strategy.HighestPriorityStrategy;

import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("Tuan 7 - quan ly thiet bi phong day");

        // tao kho cho tung loai thiet bi
        InMemoryRepository<ProjectorRemote> khoMayChieu = new InMemoryRepository<>();
        InMemoryRepository<AirConditionerRemote> khoDieuHoa = new InMemoryRepository<>();
        InMemoryRepository<WirelessMicrophone> khoMic = new InMemoryRepository<>();

        tieuDe("1. Them thiet bi vao repository");
        khoMayChieu.add(new ProjectorRemote("PR01", "Dieu khien may chieu A101", "Kho A", 90));
        khoMayChieu.add(new ProjectorRemote("PR02", "Dieu khien may chieu A102", "Kho A", 15));
        khoMayChieu.add(new ProjectorRemote("PR03", "Dieu khien may chieu A103", "Kho B", 60));

        khoDieuHoa.add(new AirConditionerRemote("AC01", "Dieu khien dieu hoa 1", "Kho A", Set.of("A201", "A202")));
        khoDieuHoa.add(new AirConditionerRemote("AC02", "Dieu khien dieu hoa 2", "Kho A", Set.of("A203", "A204")));

        khoMic.add(new WirelessMicrophone("MC01", "Micro khong day 1", "Kho B", 80, 70));
        khoMic.add(new WirelessMicrophone("MC02", "Micro khong day 2", "Kho B", 20, 90));
        khoMic.add(new WirelessMicrophone("MC03", "Micro khong day 3", "Kho C", 95, 85));

        inDanhSach(khoMayChieu.getAll());
        inDanhSach(khoDieuHoa.getAll());
        inDanhSach(khoMic.getAll());

        tieuDe("2. Them thiet bi trung ma");
        try {
            khoMayChieu.add(new ProjectorRemote("PR01", "May chieu bi trung", "Kho C", 50));
        } catch (DuplicateIdException e) {
            System.out.println("Loi: " + e.getMessage() + " (ma trung: " + e.getDuplicateId() + ")");
        }

        tieuDe("3. Tim thiet bi theo ma");
        System.out.println("Tim MC02: " + moTa(khoMic.findById("MC02")));
        System.out.println("Tim XX99: " + khoMic.findById("XX99"));

        tieuDe("4. Loc thiet bi san sang");
        inDanhSach(khoMayChieu.findAll(TeachingDevice::isReadyForUse));
        inDanhSach(khoMic.findAll(TeachingDevice::isReadyForUse));

        tieuDe("5. Chon dieu khien may chieu diem cao nhat");
        AllocationService<ProjectorRemote> dvMayChieu = new AllocationService<>(khoMayChieu,
                new HighestPriorityStrategy<>(ProjectorRemote::getDefaultPriorityScore));
        ProjectorRemote mayChieu = dvMayChieu.allocate(d -> true);
        System.out.println("Da cap phat: " + moTa(mayChieu));

        tieuDe("6. Chon dieu khien dieu hoa cho phong A203");
        AllocationService<AirConditionerRemote> dvDieuHoa =
                new AllocationService<>(khoDieuHoa, new FirstAvailableStrategy<>());
        AirConditionerRemote dieuHoa = dvDieuHoa.allocate(d -> d.isCompatibleWith("A203"));
        System.out.println("Da cap phat: " + moTa(dieuHoa));

        tieuDe("7. Chon micro dau tien san sang");
        AllocationService<WirelessMicrophone> dvMic =
                new AllocationService<>(khoMic, new FirstAvailableStrategy<>());
        WirelessMicrophone mic = dvMic.allocate(d -> true);
        System.out.println("Da cap phat: " + moTa(mic));

        tieuDe("8. Doi chien luoc khi dang chay");
        mic.returnDevice();
        System.out.println("Tra lai " + mic.getId() + " ve kho");
        dvMic.changeStrategy(new HighestPriorityStrategy<>(WirelessMicrophone::getDefaultPriorityScore));
        WirelessMicrophone mic2 = dvMic.allocate(d -> true);
        System.out.println("Chien luoc diem cao nhat chon: " + moTa(mic2));

        tieuDe("9. Cap phat khi khong co ung vien phu hop");
        try {
            dvDieuHoa.allocate(d -> d.isCompatibleWith("B101"));
        } catch (NoSuitableItemException e) {
            System.out.println("Loi: " + e.getMessage());
        }

        tieuDe("10. Thiet bi duoc chon chuyen sang Borrowed");
        System.out.println("PR01 trong kho: " + khoMayChieu.findById("PR01").getStatus());
        System.out.println("AC02 trong kho: " + khoDieuHoa.findById("AC02").getStatus());
        System.out.println("MC03 trong kho: " + khoMic.findById("MC03").getStatus());

        tieuDe("11. Mot lop repository dung cho nhieu loai thiet bi");
        System.out.println("InMemoryRepository<ProjectorRemote>: " + khoMayChieu.getAll().size() + " thiet bi");
        System.out.println("InMemoryRepository<AirConditionerRemote>: " + khoDieuHoa.getAll().size() + " thiet bi");
        System.out.println("InMemoryRepository<WirelessMicrophone>: " + khoMic.getAll().size() + " thiet bi");
    }

    // cac ham phu de in ket qua
    static void tieuDe(String s) {
        System.out.println();
        System.out.println("--- " + s + " ---");
    }

    static String moTa(TeachingDevice d) {
        if (d == null) {
            return "khong tim thay";
        }
        return d.getId() + " | " + d.getName() + " | " + d.getStorageLocation()
                + " | " + d.getStatus() + " | diem " + d.getDefaultPriorityScore();
    }

    static void inDanhSach(List<? extends TeachingDevice> ds) {
        for (TeachingDevice d : ds) {
            System.out.println("  " + moTa(d));
        }
    }
}
