package ru.selezneva;

import ru.selezneva.exceptions.IndexOfBondsException;
import ru.selezneva.exceptions.UnsupportedOperationException;

import java.util.*;
import java.util.stream.Collectors;

public class DIYarrayList<E> implements List<E> {
    private final static int INITIAL_CAPASITY = 16;
    private Object[] values = new Object[INITIAL_CAPASITY];
    private int size = 0;
    private int capasity = INITIAL_CAPASITY;

    private void increaseCapasity() {
        capasity *= 2;
        values = Arrays.copyOf(values,capasity);
    }

    private void reduceCapacity() {
        while (size < capasity / 2 && capasity > INITIAL_CAPASITY) {
            capasity /= 2;
            values = Arrays.copyOf(values,capasity);
        }
    }


    @Override
    public String toString() {
        List<Object> collect = Arrays.stream(values).filter(v -> v != null).collect(Collectors.toList());
        return "DIYarrayList{" +
                "values=" + Arrays.toString(collect.toArray()) +
                '}';
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E set(int index, E element) {
        if (index < size) {
            values[index] = element;
            return element;
        } else {
            throw new IndexOfBondsException();
        }
    }

    @Override
    public boolean add(E e) {
        if (size == capasity) {
            increaseCapasity();
        }
        values[size++] = e;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        c.stream().forEach(this::add);
        return true;
    }

    @Override
    public void clear() {
        values = new Object[INITIAL_CAPASITY];
        size = 0;
        reduceCapacity();
    }

    @Override
    public E get(int index) {
        if (index < size) {
            return (E) values[index];
        } else {
            throw new IndexOfBondsException();
        }
    }

    static <E> void copy(List<? super E> dest, List<? extends E> src) {
        dest.clear();
        for (int i = 0; i < src.size(); i++) {
            dest.add(src.get(i));
        }
    }

    static <E> void sort(List<E> list, Comparator<? super E> c) {
        Object[] objects = list.toArray();
        Arrays.sort(objects);
        for (int i = 0; i < objects.length; i++) {
            list.set(i, (E) objects[i]);
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(values, size);
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }


    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }


    //все дальнейшие методы не поддерживаются
    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
