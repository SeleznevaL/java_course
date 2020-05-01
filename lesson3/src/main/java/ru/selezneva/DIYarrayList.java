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
    protected int modCount = 0;

    private void increaseCapasity() {
        capasity *= 3;
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
        E oldValue = (E) values[index];
        values[index] = element;
        return oldValue;
    }

    @Override
    public boolean add(E e) {
        if (size == capasity) {
            increaseCapasity();
        }
        values[size++] = e;
        modCount++;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        c.stream().forEach(this::add);
        modCount++;
        return true;
    }

    @Override
    public void clear() {
        values = new Object[INITIAL_CAPASITY];
        size = 0;
        reduceCapacity();
        modCount++;
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
        int srcSize = src.size();
        if (srcSize > dest.size())
            throw new IndexOutOfBoundsException("Source does not fit in dest");
        for (int i=0; i<srcSize; i++)
                dest.set(i, src.get(i));
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
    public Iterator<E> iterator() {
        return new IteratorImpl<E>();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListIeratorImpl<E>();
    }


    //все дальнейшие методы не поддерживаются
    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
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
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private class IteratorImpl<E> implements Iterator<E> {
        int pointer;
        int lastRet = -1;
        int expectedModCount = modCount;


        @Override
        public boolean hasNext() {
            return pointer != size;
        }

        @Override
        public E next() {
            checkForComodification();
            int i = pointer;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = DIYarrayList.this.values;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            pointer = i + 1;
            return (E) elementData[lastRet = i];
        }

        protected void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    private class ListIeratorImpl<T> extends IteratorImpl<E> implements ListIterator<E> {

        @Override
        public boolean hasPrevious() {
            return pointer != 0;
        }

        @Override
        public E previous() {
            checkForComodification();
            int i = pointer - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = DIYarrayList.this.values;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            pointer = i;
            return (E) elementData[lastRet = i];
        }

        @Override
        public int nextIndex() {
            return pointer;
        }

        @Override
        public int previousIndex() {
            return pointer - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E element) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                DIYarrayList.this.set(lastRet, element);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }
    }
}
