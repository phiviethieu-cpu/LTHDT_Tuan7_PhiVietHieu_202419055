/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

package com.lthdt.tuan7.repository;

import com.lthdt.tuan7.model.Identifiable;
import com.lthdt.tuan7.exception.DuplicateIdException;

import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepository<T extends Identifiable> implements Repository<T> {
    // luu theo ma thiet bi
    private final Map<String, T> duLieu = new LinkedHashMap<>();

    // khong nhan null va ma trung
    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Khong the them null");
        }
        String id = item.getId();
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Ma doi tuong khong hop le");
        }
        if (duLieu.containsKey(id)) {
            throw new DuplicateIdException(id);
        }
        duLieu.put(id, item);
    }

    @Override
    public boolean remove(String id) {
        return duLieu.remove(id) != null;
    }

    @Override
    public T findById(String id) {
        return duLieu.get(id);
    }

    @Override
    public List<T> getAll() {
        return List.copyOf(duLieu.values());
    }

    @Override
    public List<T> findAll(Predicate<? super T> condition) {
        if (condition == null) {
            throw new IllegalArgumentException("Dieu kien loc khong duoc null");
        }
        List<T> kq = new ArrayList<>();
        for (T x : List.copyOf(duLieu.values())) {   // duyet tren ban sao
            if (condition.test(x)) {
                kq.add(x);
            }
        }
        return List.copyOf(kq);
    }
}
