package com.d20200121;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static java.util.Comparator.*;

public class IterableSortedTester {
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests whether all elements in specified iterable is sorted by specified
     * comparator.
     *
     * @param iterable   the iterable whose elements are tested.
     * @param comparator the comparator for comparing elements.
     * @param <T>        element type parameter
     * @return {@code true} if all elements in {@code iterable} is sorted;
     *         {@code false} otherwise.
     */
    public static <T> boolean test(final Iterable<? extends T> iterable, final Comparator<? super T> comparator) {
        if (iterable == null) {
            throw new NullPointerException("iterable is null");
        }
        if (comparator == null) {
            throw new NullPointerException("comparator is null");
        }
        boolean first = true;
        T previous = null;
        for (final T current : iterable) {
            if (first) {
                first = false;
                previous = current;
                continue;
            }
            if (comparator.compare(previous, current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }

    public static <T extends Comparable<? super T>> boolean testForNaturalOrder(final Iterable<? extends T> iterable) {
        return test(iterable, naturalOrder());
    }

    public static <T extends Comparable<? super T>> boolean testForReverseOrder(final Iterable<? extends T> iterable) {
        return test(iterable, reverseOrder());
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static <T> BiPredicate<Iterable<? extends T>, Comparator<? super T>> toPredicate() {
        return (i, c) -> test(i, c);
    }

    public static <T extends Comparable<? super T>> Predicate<Iterable<? extends T>> toPredicateForNaturalOrder() {
        return i -> IterableSortedTester.<T>toPredicate().test(i, naturalOrder());
    }

    public static <T extends Comparable<? super T>> Predicate<Iterable<? extends T>> toPredicateForReverseOrder() {
        return i -> IterableSortedTester.<T>toPredicate().test(i, reverseOrder());
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static void main(final String... args) {
        {
            final List<String> l = asList(args);
            final boolean natural = test(l, naturalOrder());
            final boolean reverse = test(l, reverseOrder());
            System.out.println(l + "; natural=" + natural + ", reverse=" + reverse);
        }
        {
            final List<Integer> l = asList(1, 2, 3);
            assert testForNaturalOrder(l);
            assert !testForReverseOrder(l);
            assert IterableSortedTester.<Integer>toPredicateForNaturalOrder().test(l);
            assert !IterableSortedTester.<Integer>toPredicateForReverseOrder().test(l);
        }
        {
            final List<Integer> l = asList(1, 2, 3, null);
            assert test(l, nullsLast(naturalOrder()));
            assert !test(l, nullsLast(reverseOrder()));
        }
        {
            final List<Integer> l = asList(3, 2, 1);
            assert !testForNaturalOrder(l);
            assert testForReverseOrder(l);
            assert !IterableSortedTester.<Integer>toPredicateForNaturalOrder().test(l);
            assert IterableSortedTester.<Integer>toPredicateForReverseOrder().test(l);
        }
        {
            final List<Integer> l = asList(null, 3, 2, 1);
            assert !test(l, nullsFirst(naturalOrder()));
            assert test(l, nullsFirst(reverseOrder()));
        }
    }

    /**
     * Creates a new instance.
     */
    private IterableSortedTester() {
        super();
    }
}
