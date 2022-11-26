package com.stefanpetcu.mergeoverlappingintervals.application;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;

public class OverlappingIntervalsMergerServiceImpl implements OverlappingIntervalsMergerService {
    Comparator<Entry<Integer, Integer>> keySorter = Entry.comparingByKey();
    Comparator<Entry<Integer, Integer>> valueSorter = Entry.comparingByValue();

    @Override
    public List<SimpleImmutableEntry<Integer, Integer>> mergeOverlappingIntervals(List<SimpleImmutableEntry<Integer, Integer>> input) {
        var intervals = new ArrayList<>(input);
        intervals.sort(keySorter.thenComparing(valueSorter));

        ListIterator<SimpleImmutableEntry<Integer, Integer>> iterator = intervals.listIterator();

        while (iterator.hasNext()) {
            var current = iterator.next();

            if (!iterator.hasNext()) {
                break;
            }

            var next = iterator.next();

            if (current.getKey() <= next.getKey() && current.getValue() >= next.getValue()) {
                // The next interval is wholly subsumed by the current one. (case 1)
                iterator.remove();
            } else if (current.getValue() >= next.getKey()) {
                // The current & next intervals have some overlap. (case 2)
                iterator.remove();
                iterator.previous();
                iterator.remove();
                iterator.add(new SimpleImmutableEntry<>(current.getKey(), next.getValue()));
            }

            // Rewind the iterator to make sure we don't skip comparing the current element with the next-next one,
            // after the current element subsumed the next one (case 1), or skip over elements we added (case 2).
            iterator.previous();
        }

        return intervals;
    }
}
