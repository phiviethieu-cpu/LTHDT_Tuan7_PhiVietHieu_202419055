/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

package com.lthdt.tuan7.service;

import com.lthdt.tuan7.model.TeachingDevice;
import com.lthdt.tuan7.repository.Repository;
import com.lthdt.tuan7.strategy.SelectionStrategy;

import java.util.function.Predicate;
import java.util.List;

public class AllocationService<T extends TeachingDevice> {
    private final Repository<T> repository;
    private SelectionStrategy<T> strategy;

    public AllocationService(Repository<T> repository, SelectionStrategy<T> strategy) {
        if (repository == null || strategy == null) {
            throw new IllegalArgumentException("Repository va strategy khong duoc null");
        }
        this.repository = repository;
        this.strategy = strategy;
    }

    public void changeStrategy(SelectionStrategy<T> newStrategy) {
        if (newStrategy == null) {
            throw new IllegalArgumentException("Strategy khong duoc null");
        }
        this.strategy = newStrategy;
    }

    public T allocate(Predicate<T> additionalCondition) {
        // khong truyen dieu kien thi chi loc thiet bi san sang
        Predicate<T> dieuKien = additionalCondition != null ? additionalCondition : d -> true;

        // loc thiet bi san sang va thoa dieu kien
        List<T> ungVien = repository.findAll(d -> d.isReadyForUse() && dieuKien.test(d));

        // chon thiet bi roi cho muon
        T chon = strategy.select(ungVien);
        chon.borrow();
        return chon;
    }
}
