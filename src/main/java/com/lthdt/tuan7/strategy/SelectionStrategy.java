package com.lthdt.tuan7.strategy;

import java.util.List;

public interface SelectionStrategy<T> {
    T select(List<T> candidates);
}
