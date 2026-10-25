package com.lthdt.tuan7.repository;

import com.lthdt.tuan7.model.Identifiable;

import java.util.List;
import java.util.function.Predicate;

public interface Repository<T extends Identifiable> {
    void add(T item);

    boolean remove(String id);

    T findById(String id);

    List<T> getAll();

    List<T> findAll(Predicate<T> condition);
}
