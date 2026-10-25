package com.lthdt.tuan7.strategy;

import com.lthdt.tuan7.exception.NoSuitableItemException;

import java.util.List;

public class FirstAvailableStrategy<T> implements SelectionStrategy<T> {

    @Override
    public T select(List<T> candidates) {
        if (candidates == null || candidates.isEmpty()) {
            throw new NoSuitableItemException();
        }
        return candidates.get(0);
    }
}
