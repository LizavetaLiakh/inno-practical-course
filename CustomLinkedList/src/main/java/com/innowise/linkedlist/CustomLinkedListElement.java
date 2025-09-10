package com.innowise.linkedlist;

public class CustomLinkedListElement<T> {
    private T value;
    private CustomLinkedListElement<T> prevValue;
    private CustomLinkedListElement<T> nextValue;

    public CustomLinkedListElement(T value) {
        setValue(value);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public CustomLinkedListElement<T> getPrevValue() {
        return prevValue;
    }

    public void setPrevValue(CustomLinkedListElement<T> prevValue) {
        this.prevValue = prevValue;
    }

    public CustomLinkedListElement<T> getNextValue() {
        return nextValue;
    }

    public void setNextValue(CustomLinkedListElement<T> nextValue) {
        this.nextValue = nextValue;
    }
}
