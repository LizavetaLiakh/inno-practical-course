package org.example;

public class CustomLinkedList<T> {
    private CustomLinkedListElement<T> currentElement;

    public int size() {
        if (currentElement == null) {
            return 0;
        }
        setCurrentToFirst();
        int countElements = 1;
        while(currentElement.getNextValue() != null) {
            setCurrentElement(currentElement.getNextValue());
            countElements++;
        }
        return countElements;
    }

    public void addFirst(T el) {
        if (currentElement == null) {
            CustomLinkedListElement<T> newElement = new CustomLinkedListElement<>(el);
            setCurrentElement(newElement);
        } else {
            setCurrentToFirst();
            CustomLinkedListElement<T> newElement = new CustomLinkedListElement<>(el);
            newElement.setNextValue(currentElement);
            currentElement.setPrevValue(newElement);
        }
    }

    public void addLast(T el) {
        if (currentElement == null) {
            CustomLinkedListElement<T> newElement = new CustomLinkedListElement<>(el);
            setCurrentElement(newElement);
        } else {
            setCurrentToLast();
            CustomLinkedListElement<T> newElement = new CustomLinkedListElement<>(el);
            newElement.setPrevValue(currentElement);
            currentElement.setNextValue(newElement);
        }
    }

    public void add(int index, T el) {
        int listSize = size();

        if(index < 0 || index > listSize) {
            throw new IndexOutOfBoundsException("Index should be more than -1 and less than list size.");
        }

        if (index == 0) {
            addFirst(el);
        } else if(index == listSize) {
            addLast(el);
        } else {
            setCurrent(index);
            CustomLinkedListElement<T> newElement = new CustomLinkedListElement<>(el);
            CustomLinkedListElement<T> prevElement = currentElement.getPrevValue();
            prevElement.setNextValue(newElement);
            newElement.setPrevValue(prevElement);
            newElement.setNextValue(currentElement);
            currentElement.setPrevValue(newElement);
        }
    }

    public T getFirst() {
        if (currentElement == null) {
            return null;
        }
        setCurrentToFirst();
        return currentElement.getValue();
    }

    public T getLast() {
        if (currentElement == null) {
            return null;
        }
        setCurrentToLast();
        return currentElement.getValue();
    }

    public T get(int index) {
        if (currentElement == null) {
            return null;
        }
        setCurrent(index);
        return currentElement.getValue();
    }

    public T removeFirst() {
        if (currentElement == null) {
            return null;
        }

        setCurrentToFirst();
        CustomLinkedListElement<T> nextElement = currentElement.getNextValue();
        T retrievableValue = currentElement.getValue();
        currentElement = null;
        if (nextElement != null) {
            nextElement.setPrevValue(null);
        }
        setCurrentElement(nextElement);

        return retrievableValue;
    }

    public T removeLast() {
        if (currentElement == null) {
            return null;
        }

        setCurrentToLast();
        CustomLinkedListElement<T> prevElement = currentElement.getPrevValue();
        T retrievableValue = currentElement.getValue();
        currentElement = null;
        if (prevElement != null) {
            prevElement.setNextValue(null);
        }
        setCurrentElement(prevElement);

        return retrievableValue;
    }

    public T remove(int index) {
        if (currentElement == null) {
            return null;
        }

        setCurrent(index);
        CustomLinkedListElement<T> prevElement = currentElement.getPrevValue();
        CustomLinkedListElement<T> nextElement = currentElement.getNextValue();
        T retrievableValue = currentElement.getValue();
        currentElement = null;
        if (prevElement != null) {
            prevElement.setNextValue(nextElement);
            setCurrentElement(prevElement);
        }
        if (nextElement != null) {
            nextElement.setPrevValue(prevElement);
            if (currentElement == null) setCurrentElement(nextElement);
        }

        return retrievableValue;
    }

    private void setCurrent(int index) {
        if (index > -1 && index < size()) {
            setCurrentToFirst();
            int currentIndex = 0;
            while (currentIndex != index) {
                setCurrentElement(currentElement.getNextValue());
                currentIndex++;
            }
        } else {
            throw new IndexOutOfBoundsException("Index should be more than -1 and less than list size.");
        }
    }

    private void setCurrentToFirst() {
        while (currentElement.getPrevValue() != null) {
            setCurrentElement(currentElement.getPrevValue());
        }
    }

    private void setCurrentToLast() {
        while (currentElement.getNextValue() != null) {
            setCurrentElement(currentElement.getNextValue());
        }
    }

    private void setCurrentElement(CustomLinkedListElement<T> element) {
        this.currentElement = element;
    }
}
