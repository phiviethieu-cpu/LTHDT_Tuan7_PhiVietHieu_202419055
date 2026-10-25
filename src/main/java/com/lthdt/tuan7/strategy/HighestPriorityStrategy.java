/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

package com.lthdt.tuan7.strategy;

import java.util.function.ToDoubleFunction;
import java.util.List;

import com.lthdt.tuan7.exception.NoSuitableItemException;

public class HighestPriorityStrategy<T> implements SelectionStrategy<T> {
    private final ToDoubleFunction<? super T> scoreSelector;

    public HighestPriorityStrategy(ToDoubleFunction<? super T> scoreSelector) {
        if (scoreSelector == null) {
            throw new IllegalArgumentException("Ham tinh diem khong duoc null");
        }
        this.scoreSelector = scoreSelector;
    }

    @Override
    public T select(List<T> candidates) {
        if (candidates == null || candidates.isEmpty()) {
            throw new NoSuitableItemException();
        }

        // tim phan tu co diem cao nhat
        T best = null;
        double maxDiem = 0;
        for (T x : candidates) {
            double diem = scoreSelector.applyAsDouble(x);
            if (Double.isNaN(diem)) {
                throw new IllegalStateException("Diem khong hop le (NaN) cho: " + x);
            }
            if (best == null || diem > maxDiem) {   // bang diem thi giu phan tu dau
                best = x;
                maxDiem = diem;
            }
        }
        return best;
    }
}
