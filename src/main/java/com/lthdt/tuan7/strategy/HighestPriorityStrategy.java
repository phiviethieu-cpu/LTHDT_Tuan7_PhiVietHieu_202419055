package com.lthdt.tuan7.strategy;

import java.util.function.ToDoubleFunction;
import java.util.List;

import com.lthdt.tuan7.exception.NoSuitableItemException;

public class HighestPriorityStrategy<T> implements SelectionStrategy<T> {
    private final ToDoubleFunction<T> scoreSelector;

    public HighestPriorityStrategy(ToDoubleFunction<T> scoreSelector) {
        this.scoreSelector = scoreSelector;
    }

    @Override
    public T select(List<T> candidates) {
        if (candidates.isEmpty()) {
            throw new NoSuitableItemException();
        }

        // tim phan tu co diem cao nhat
        T best = null;
        double maxDiem = Double.NEGATIVE_INFINITY;
        for (T x : candidates) {
            double diem = scoreSelector.applyAsDouble(x);
            if (diem >= maxDiem) {
                maxDiem = diem;
                best = x;
            }
        }
        return best;
    }
}
