package ics211tester.tests;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AssertingList<E> implements List<E> {

    private final List<E> delegate;

    public AssertingList(List<E> delegate) {
        this.delegate = delegate;
    }

    private void assertAllowedMethod(String methodName) {
        if (!methodName.equals("get") && !methodName.equals("size")) {
            throw new AssertionError("Method call not allowed: " + methodName);
        }
    }

    @Override
    public int size() {
        assertAllowedMethod("size");
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        assertAllowedMethod("isEmpty");
        return delegate.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        assertAllowedMethod("contains");
        return delegate.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        assertAllowedMethod("iterator");
        return new AssertingIterator(delegate.iterator());
    }

    @Override
    public Object[] toArray() {
        assertAllowedMethod("toArray");
        return delegate.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        assertAllowedMethod("toArray");
        return delegate.toArray(a);
    }

    @Override
    public boolean add(E e) {
        assertAllowedMethod("add");
        return delegate.add(e);
    }

    @Override
    public boolean remove(Object o) {
        assertAllowedMethod("remove");
        return delegate.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        assertAllowedMethod("containsAll");
        return delegate.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        assertAllowedMethod("addAll");
        return delegate.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        assertAllowedMethod("addAll");
        return delegate.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        assertAllowedMethod("removeAll");
        return delegate.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        assertAllowedMethod("retainAll");
        return delegate.retainAll(c);
    }

    @Override
    public void clear() {
        assertAllowedMethod("clear");
        delegate.clear();
    }

    @Override
    public E get(int index) {
        assertAllowedMethod("get");
        return delegate.get(index);
    }

    @Override
    public E set(int index, E element) {
        assertAllowedMethod("set");
        return delegate.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        assertAllowedMethod("add");
        delegate.add(index, element);
    }

    @Override
    public E remove(int index) {
        assertAllowedMethod("remove");
        return delegate.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        assertAllowedMethod("indexOf");
        return delegate.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        assertAllowedMethod("lastIndexOf");
        return delegate.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        assertAllowedMethod("listIterator");
        return new AssertingListIterator(delegate.listIterator());
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        assertAllowedMethod("listIterator");
        return new AssertingListIterator(delegate.listIterator(index));
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        assertAllowedMethod("subList");
        return new AssertingList<>(delegate.subList(fromIndex, toIndex));
    }

    private class AssertingIterator implements Iterator<E> {

        private final Iterator<E> delegate;

        public AssertingIterator(Iterator<E> delegate) {
            this.delegate = delegate;
        }

        @Override
        public boolean hasNext() {
            assertAllowedMethod("hasNext");
            return delegate.hasNext();
        }

        @Override
        public E next() {
            assertAllowedMethod("next");
            return delegate.next();
        }

        @Override
        public void remove() {
            assertAllowedMethod("remove");
            delegate.remove();
        }
    }

    private class AssertingListIterator extends AssertingIterator implements ListIterator<E> {

        private final ListIterator<E> delegate;

        public AssertingListIterator(ListIterator<E> delegate) {
            super(delegate);
            this.delegate = delegate;
        }

        @Override
        public boolean hasPrevious() {
            assertAllowedMethod("hasPrevious");
            return delegate.hasPrevious();
        }

        @Override
        public E previous() {
            assertAllowedMethod("previous");
            return delegate.previous();
        }

        @Override
        public int nextIndex() {
            assertAllowedMethod("nextIndex");
            return delegate.nextIndex();
        }

        @Override
        public int previousIndex() {
            assertAllowedMethod("previousIndex");
            return delegate.previousIndex();
        }

        @Override
        public void set(E e) {
            assertAllowedMethod("set");
            delegate.set(e);
        }

        @Override
        public void add(E e) {
            assertAllowedMethod("add");
            delegate.add(e);
        }
    }
}