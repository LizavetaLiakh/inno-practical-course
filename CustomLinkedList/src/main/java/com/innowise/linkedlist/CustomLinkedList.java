package com.innowise.linkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomLinkedList<T> implements Iterable<T> {

    private CustomLinkedListElement<T> head;
    private CustomLinkedListElement<T> tail;
    private int size;

    /**
     *
     * @return the amount of all elements in the list
     */
    public int size() {
        return size;
    }

    /**
     *
     * @param el element that will be added at the beginning
     */
    public void addFirst(T el) {
        CustomLinkedListElement<T> newElement = new CustomLinkedListElement<>(el);
        if (head == null) {
            head = tail = newElement;
        } else {
            newElement.setNextValue(head);
            head.setPrevValue(newElement);
            head = newElement;
        }
        size++;
    }

    /**
     *
     * @param el element that will be added at the end
     */
    public void addLast(T el) {
        CustomLinkedListElement<T> newElement = new CustomLinkedListElement<>(el);
        if (tail == null) {
            head = tail = newElement;
        } else {
            tail.setNextValue(newElement);
            newElement.setPrevValue(tail);
            tail = newElement;
        }
        size++;
    }

    /**
     *
     * @param index index of the new element
     * @param el element that will be added
     */
    public void add(int index, T el) {
        if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index should be between 0 and " + size());
        }

        if (index == 0) {
            addFirst(el);
        } else if(index == size) {
            addLast(el);
        } else {
            CustomLinkedListElement<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNextValue();
            }
            CustomLinkedListElement<T> prev = current.getPrevValue();
            CustomLinkedListElement<T> newElement = new CustomLinkedListElement<>(el);
            newElement.setPrevValue(prev);
            newElement.setNextValue(current);
            prev.setNextValue(newElement);
            current.setPrevValue(newElement);

            size++;
        }
    }

    /**
     *
     * @return returns the first element in the list
     */
    public T getFirst() {
        if (head == null || tail == null) {
            return null;
        }
        return head.getValue();
    }

    /**
     *
     * @return returns the last element in the list
     */
    public T getLast() {
        if (tail == null) {
            return null;
        }
        return tail.getValue();
    }

    /**
     *
     * @param index the index of the element you want to get
     * @return returns the element on the index position
     */
    public T get(int index) {
        if (head == null || tail == null) {
            return null;
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index should be between 0 and " + size);
        }
        CustomLinkedListElement<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNextValue();
        }
        return current.getValue();
    }

    /**
     *
     * @return returns the value of the first element in the list and deletes it
     */
    public T removeFirst() {
        if (head == null || tail == null) {
            return null;
        }
        T retrievalValue = head.getValue();
        head = head.getNextValue();
        if (head != null) {
            head.setPrevValue(null);
        } else {
            tail = null;
        }

        size--;
        return retrievalValue;
    }

    /**
     *
     * @return returns the value of the last element in the list and deletes it
     */
    public T removeLast() {
        if (head == null || tail == null) {
            return null;
        }
        T retrievalValue = tail.getValue();
        tail = tail.getPrevValue();
        if (tail != null) {
            tail.setNextValue(null);
        } else {
            head = null;
        }

        size--;
        return retrievalValue;
    }

    /**
     *
     * @param index the index of the element you want to delete
     * @return returns the value of the element on the index position in the list and deletes it
     */
    public T remove(int index) {
        if (head == null || tail == null) {
            return null;
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index should be between 0 and " + size);
        }
        CustomLinkedListElement<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNextValue();
        }
        CustomLinkedListElement<T> prev = current.getPrevValue();
        CustomLinkedListElement<T> next = current.getNextValue();

        if (prev != null) {
            prev.setNextValue(next);
        } else {
            head = next;
        }

        if (next != null) {
            next.setPrevValue(prev);
        } else {
            tail = prev;
        }

        size--;
        return current.getValue();
    }

    /**
     * Clears the list
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     *
     * @return returns true if the list is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     *
     * @param o the element you want to check
     * @return returns true if the list has o
     */
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    /**
     *
     * @param o the element you want to get index of
     * @return returns the index of element o
     */
    public int indexOf(Object o) {
        int index = 0;
        for (CustomLinkedListElement<T> current = head; current != null; current = current.getNextValue()) {
            if (o == null ? current.getValue() == null : o.equals(current.getValue())) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        CustomLinkedListElement<T> current = head;
        while (current != null) {
            sb.append(current.getValue());
            if (current.getNextValue() != null) {
                sb.append(", ");
            }
            current = current.getNextValue();
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private CustomLinkedListElement<T> current;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T value = current.getValue();
                current = current.getNextValue();
                return value;
            }
        };
    }
}
