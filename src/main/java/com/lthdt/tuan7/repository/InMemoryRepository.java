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

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Khong the them null");
        }
        if (duLieu.containsKey(item.getId())) {
            throw new DuplicateIdException(item.getId());
        }
        duLieu.put(item.getId(), item);
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
    public List<T> findAll(Predicate<T> condition) {
        List<T> kq = new ArrayList<>();
        for (T x : duLieu.values()) {
            if (condition.test(x)) {
                kq.add(x);
            }
        }
        return List.copyOf(kq);
    }
}
