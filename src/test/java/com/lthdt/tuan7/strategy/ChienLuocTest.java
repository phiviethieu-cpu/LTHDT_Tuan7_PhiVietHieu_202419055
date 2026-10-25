/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

package com.lthdt.tuan7.strategy;

import com.lthdt.tuan7.exception.NoSuitableItemException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChienLuocTest {

    // chien luoc generic, khong can la thiet bi
    @Test
    void chonSoLonNhat() {
        HighestPriorityStrategy<Integer> cl = new HighestPriorityStrategy<>(x -> x);
        assertEquals(9, cl.select(List.of(3, 9, 5)));
    }

    @Test
    void chonChuoiDaiNhat() {
        HighestPriorityStrategy<String> cl = new HighestPriorityStrategy<>(String::length);
        assertEquals("A203-B", cl.select(List.of("A1", "A203-B", "B12")));
    }

    @Test
    void chonPhanTuDauTien() {
        assertEquals("a", new FirstAvailableStrategy<String>().select(List.of("a", "b", "c")));
    }

    @Test
    void danhSachRongThiBaoLoi() {
        assertThrows(NoSuitableItemException.class,
                () -> new HighestPriorityStrategy<Integer>(x -> x).select(List.of()));
        assertThrows(NoSuitableItemException.class,
                () -> new FirstAvailableStrategy<Integer>().select(List.of()));
    }

    @Test
    void khongSuaDanhSachDauVao() {
        List<Integer> ds = new ArrayList<>(List.of(4, 8, 1));
        new HighestPriorityStrategy<Integer>(x -> x).select(ds);
        new FirstAvailableStrategy<Integer>().select(ds);

        assertEquals(List.of(4, 8, 1), ds);
    }
}
