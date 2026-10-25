/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

package com.lthdt.tuan7.repository;

import com.lthdt.tuan7.model.Identifiable;

import java.util.List;
import java.util.function.Predicate;

// kho luu tru tong quat, T phai co ma
public interface Repository<T extends Identifiable> {
    void add(T item);

    boolean remove(String id);

    T findById(String id);

    List<T> getAll();

    List<T> findAll(Predicate<? super T> condition);
}
