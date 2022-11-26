package com.stefanpetcu.mergeoverlappingintervals.application;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;

public interface OverlappingIntervalsMergerService {
    List<SimpleImmutableEntry<Integer, Integer>> mergeOverlappingIntervals(List<SimpleImmutableEntry<Integer, Integer>> input);
}
