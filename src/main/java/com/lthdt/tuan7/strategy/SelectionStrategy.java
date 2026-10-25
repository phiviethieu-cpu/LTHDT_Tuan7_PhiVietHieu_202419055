/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

package com.lthdt.tuan7.strategy;

import java.util.List;

// chon 1 phan tu trong danh sach ung vien
public interface SelectionStrategy<T> {
    T select(List<T> candidates);
}
