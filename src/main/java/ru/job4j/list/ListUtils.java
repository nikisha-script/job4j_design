package ru.job4j.list;

import java.util.*;
import java.util.function.Predicate;

public class ListUtils {

    public static <T> void addBefore(List<T> list, int index, T value) {
        Objects.checkIndex(index, list.size());
        ListIterator<T> iterator = list.listIterator();
        while (iterator.hasNext()) {
            if (iterator.nextIndex() == index) {
                iterator.add(value);
                break;
            }
            iterator.next();
        }
    }

    public static <T> void addAfter(List<T> list, int index, T value) {
        Objects.checkIndex(index, list.size());
        ListIterator<T> iterator = list.listIterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        iterator.add(value);
    }

    public static <T> void removeIf(List<T> list, Predicate<T> filter) {
        ListIterator<T> iterator = list.listIterator();
        while (iterator.hasNext()) {
            if (filter.test(iterator.next())) {
                iterator.remove();
                break;
            }
            iterator.next();
        }
    }

    public static <T> void replaceIf(List<T> list, Predicate<T> filter, T value) {
        ListIterator<T> iterator = list.listIterator();
        while (iterator.hasNext()) {
            if (filter.test(iterator.next())) {
                iterator.set(value);
                break;
            }
            iterator.next();
        }
    }

    public static <T> void removeAll(List<T> list, List<T> elements) {
        ListIterator<T> first = list.listIterator();
        while (first.hasNext()) {
            if (elements.contains(first.next())) {
                first.remove();
            }
        }
    }

}