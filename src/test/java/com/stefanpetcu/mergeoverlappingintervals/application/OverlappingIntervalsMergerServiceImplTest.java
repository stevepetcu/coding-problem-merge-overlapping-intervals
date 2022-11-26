package com.stefanpetcu.mergeoverlappingintervals.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class OverlappingIntervalsMergerServiceImplTest {
    private final OverlappingIntervalsMergerService service = new OverlappingIntervalsMergerServiceImpl();

    static private Stream<Arguments> inputsAndOutputsProvider() {
        return Stream.of(
                arguments(
                        List.of(
                                new SimpleImmutableEntry<>(5, 8),
                                new SimpleImmutableEntry<>(1, 3),
                                new SimpleImmutableEntry<>(20, 25)
                        ),
                        List.of(
                                new SimpleImmutableEntry<>(1, 3),
                                new SimpleImmutableEntry<>(5, 8),
                                new SimpleImmutableEntry<>(20, 25)
                        )
                ),
                arguments(List.of(new SimpleImmutableEntry<>(1, 3)), List.of(new SimpleImmutableEntry<>(1, 3))),
                arguments(List.of(), List.of())
        );
    }

    @ParameterizedTest
    @MethodSource("inputsAndOutputsProvider")
    void mergeOverlappingIntervals_returns_sorted_input_when_there_are_not_overlapping_intervals(List<SimpleImmutableEntry<Integer, Integer>> input, List<SimpleImmutableEntry<Integer, Integer>> expectedOutput) {
        assertEquals(expectedOutput, service.mergeOverlappingIntervals(input));
    }

    @Test
    void mergeOverlappingIntervals_returns_list_of_merged_overlapping_intervals_when_there_are_full_overlapping_intervals() {
        var input = List.of(
                new SimpleImmutableEntry<>(1, 3),
                new SimpleImmutableEntry<>(5, 8),
                new SimpleImmutableEntry<>(4, 10),
                new SimpleImmutableEntry<>(20, 25)
        );
        var expectedOutput = List.of(
                new SimpleImmutableEntry<>(1, 3),
                new SimpleImmutableEntry<>(4, 10),
                new SimpleImmutableEntry<>(20, 25)
        );

        assertEquals(expectedOutput, service.mergeOverlappingIntervals(input));
    }

    @Test
    void mergeOverlappingIntervals_returns_list_of_merged_overlapping_intervals_when_there_are_half_overlapping_intervals() {
        var input = List.of(
                new SimpleImmutableEntry<>(0, 5),
                new SimpleImmutableEntry<>(5, 10),
                new SimpleImmutableEntry<>(15, 20),
                new SimpleImmutableEntry<>(10, 15),
                new SimpleImmutableEntry<>(30, 40),
                new SimpleImmutableEntry<>(28, 35),
                new SimpleImmutableEntry<>(45, 55),
                new SimpleImmutableEntry<>(47, 57),
                new SimpleImmutableEntry<>(60, 65),
                new SimpleImmutableEntry<>(65, 67),
                new SimpleImmutableEntry<>(67, 70)
        );
        var expectedOutput = List.of(
                new SimpleImmutableEntry<>(0, 20),
                new SimpleImmutableEntry<>(28, 40),
                new SimpleImmutableEntry<>(45, 57),
                new SimpleImmutableEntry<>(60, 70)
        );

        assertEquals(expectedOutput, service.mergeOverlappingIntervals(input));
    }

    @Test
    void mergeOverlappingIntervals_returns_list_of_merged_overlapping_intervals_for_negative_values() {
        var input = List.of(
                new SimpleImmutableEntry<>(-10, -1),
                new SimpleImmutableEntry<>(-8, -5),
                new SimpleImmutableEntry<>(-4, 3),
                new SimpleImmutableEntry<>(-20, -12),
                new SimpleImmutableEntry<>(-15, -11),
                new SimpleImmutableEntry<>(-5, -4)
        );
        var expectedOutput = List.of(
                new SimpleImmutableEntry<>(-20, -11),
                new SimpleImmutableEntry<>(-10, 3)
        );

        assertEquals(expectedOutput, service.mergeOverlappingIntervals(input));
    }
}
