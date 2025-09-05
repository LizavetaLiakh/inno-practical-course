package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomLinkedListTest {

    @Test
    @DisplayName("Empty list size")
    void testSizeEmpty() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        assertEquals(0, colors.size(), "Empty list size has to be 0.");
    }

    @Test
    @DisplayName("Non-empty list size")
    void testSizeNonEmpty() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Red");
        colors.addLast("Orange");
        colors.addLast("Yellow");

        assertEquals(3, colors.size(), "List size has to be 3.");
    }

    @Test
    @DisplayName("Add the element in the beginning of the empty list")
    void testAddFirstEmpty() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addFirst("Red");

        assertEquals("Red", colors.getFirst(), "The first element has to be 'Red'");
    }

    @Test
    @DisplayName("Add the element in the beginning of the non-empty list")
    void testAddFirstNonEmpty() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addFirst("Orange");
        colors.addFirst("Red");

        assertEquals("Red", colors.getFirst(), "The first element has to be 'Red'");
    }

    @Test
    @DisplayName("Add the element in the end of the empty list")
    void testAddLastEmpty() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Purple");

        assertEquals("Purple", colors.getLast(), "The last element has to be 'Purple'");
    }

    @Test
    @DisplayName("Add the element in the end of the non-empty list")
    void testAddLastNonEmpty() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Indigo");
        colors.addLast("Purple");

        assertEquals("Purple", colors.getLast(), "The last element has to be 'Purple'");
    }

    @Test
    @DisplayName("Add the element in the list by index")
    void testAdd() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Red");
        colors.addLast("Orange");
        colors.add(1, "Yellow");

        assertEquals("Red", colors.get(0), "The element 'Red' has to have index 0.");
        assertEquals("Yellow", colors.get(1), "The element with index 1 has to be 'Yellow'.");
        assertEquals("Orange", colors.get(2), "The element 'Orange' has to have index 3.");
    }

    @Test
    @DisplayName("Add the element in the list by index to the end")
    void testAddToTheEnd() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Red");
        colors.addLast("Orange");
        colors.add(2, "Yellow");

        assertEquals("Yellow", colors.get(2), "The element with index 2 (the last element) has to" +
                " be 'Yellow'.");
    }

    @Test
    @DisplayName("Add the element in the list by negative index")
    void testAddByNegativeIndex() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Red");

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> colors.add(-1, "Yellow"));
        assertEquals("Index should be between 0 and " + colors.size(), exception.getMessage());
    }

    @Test
    @DisplayName("Add the element in the list by bigger index than list size")
    void testAddByBiggerIndex() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Red");

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> colors.add(5, "Yellow"));
        assertEquals("Index should be between 0 and " + colors.size(), exception.getMessage());
    }

    @Test
    @DisplayName("Get first element")
    void testGetFirst() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Red");
        colors.addLast("Orange");

        assertEquals("Red", colors.getFirst(), "The first element has to be 'Red'.");
    }

    @Test
    @DisplayName("Get first element from the empty list")
    void testGetFirstEmptyList() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        assertNull(colors.getFirst(), "The list is empty so the first element is null.");
    }

    @Test
    @DisplayName("Get last element")
    void testGetLast() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Indigo");
        colors.addLast("Purple");

        assertEquals("Purple", colors.getLast(), "The last element has to be 'Purple'.");
    }

    @Test
    @DisplayName("Get last element from the empty list")
    void testGetLastEmptyList() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        assertNull(colors.getLast(), "The list is empty so the last element is null.");
    }

    @Test
    @DisplayName("Get the element by index")
    void testGet() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Red");
        colors.addLast("Orange");
        colors.addLast("Yellow");

        assertEquals("Red", colors.get(0), "The element with index 0 has to be 'Red'.");
        assertEquals("Orange", colors.get(1), "The element with index 1 has to be 'Orange'.");
        assertEquals("Yellow", colors.get(2), "The element with index 2 has to be 'Yellow'.");
    }

    @Test
    @DisplayName("Get the element by index from the empty list")
    void testGetEmptyList() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        assertNull(colors.get(1), "The are no elements and the list is empty.");
    }

    @Test
    @DisplayName("Retrieve and remove the first element of the list")
    void testRemoveFirst() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Red");
        colors.addLast("Orange");

        assertEquals("Red", colors.removeFirst(), "The retrieval element has to be 'Red'.");
        assertEquals("Orange", colors.getFirst(), "The first element after the removal has to be " +
                "'Orange'.");
    }

    @Test
    @DisplayName("Retrieve and remove the first element of empty list")
    void testRemoveFirstEmptyList() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        assertNull(colors.removeFirst(), "The list is empty so it's impossible to get any elements.");
    }

    @Test
    @DisplayName("Retrieve and remove the first element of list with 1 element")
    void testRemoveFirstListWithOneElement() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Red");

        assertEquals("Red", colors.removeFirst(), "The retrieval element has to be 'Red'.");
        assertNull(colors.getFirst(), "TThe list is empty so it's impossible to get any elements.");
    }

    @Test
    @DisplayName("Retrieve and remove the last element of the list")
    void testRemoveLast() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Indigo");
        colors.addLast("Purple");

        assertEquals("Purple", colors.removeLast(), "The retrieval element has to be 'Purple'.");
        assertEquals("Indigo", colors.getLast(), "The last element after the removal has to be " +
                "'Indigo'.");
    }

    @Test
    @DisplayName("Retrieve and remove the last element of empty list")
    void testRemoveLastEmptyList() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        assertNull(colors.removeLast(), "The list is empty so it's impossible to get any elements.");
    }

    @Test
    @DisplayName("Retrieve and remove the last element of list with 1 element")
    void testRemoveLastListWithOneElement() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Purple");

        assertEquals("Purple", colors.removeLast(), "The retrieval element has to be 'Purple'.");
        assertNull(colors.getLast(), "TThe list is empty so it's impossible to get any elements.");
    }

    @Test
    @DisplayName("Retrieve and remove the last element of the list by index")
    void testRemove() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Red");
        colors.addLast("Orange");
        colors.addLast("Yellow");

        assertEquals("Orange", colors.remove(1), "The retrieval element (index 1) has to be" +
                " 'Orange'.");
        assertEquals("Red", colors.get(0), "The element with index 0 after the removal has to" +
                " be 'Red'.");
        assertEquals("Yellow", colors.get(1), "The element with index 1 after the removal has to" +
                " be 'Yellow'.");
        assertEquals(2, colors.size(), "The size after the removal has to be 2.");
    }

    @Test
    @DisplayName("Retrieve and remove the last element of empty list by index")
    void testRemoveEmptyList() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        assertNull(colors.get(1), "The are no elements and the list is empty.");
    }

    @Test
    @DisplayName("Retrieve and remove the last element of list by correct index with 1 element")
    void testRemoveListWithOneElement() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Red");

        assertEquals("Red", colors.remove(0), "The retrieval element has to be 'Red'.");
        assertNull(colors.get(0), "The list is empty so it's impossible to get any elements.");
        assertEquals(0, colors.size(), "The size of the list is 0 after the removal.");
    }

    @Test
    @DisplayName("Retrieve and remove the element of the list by the negative index")
    void testRemoveNegativeIndex() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Red");

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> colors.remove(-2));
        assertEquals("Index should be between 0 and " + colors.size(), exception.getMessage());
    }

    @Test
    @DisplayName("Retrieve and remove the element of the list by the bigger index")
    void testRemoveBiggerIndex() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Red");
        colors.addLast("Orange");

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> colors.remove(3));
        assertEquals("Index should be between 0 and " + colors.size(), exception.getMessage());
    }

    @Test
    @DisplayName("Clear linked list")
    void testClear() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Red");
        colors.addLast("Orange");
        colors.clear();

        assertNull(colors.getFirst(), "The first element should be null.");
        assertNull(colors.getLast(), "The last element should be null.");
        assertEquals(0, colors.size(), "The size of the list should be 0.");
    }

    @Test
    @DisplayName("Check that list is not empty")
    void testIsEmptyFalse() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Red");

        assertFalse(colors.isEmpty(), "The list is not empty.");
    }

    @Test
    @DisplayName("Check that list is empty")
    void testIsEmptyTrue() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        assertTrue(colors.isEmpty(), "The list is empty.");
    }

    @Test
    @DisplayName("Check that list contains some object")
    void testContainsTrue() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Red");
        colors.addLast("Orange");

        assertTrue(colors.contains("Orange"), "The list should contain Orange color.");
    }

    @Test
    @DisplayName("Check that list doesn't contain some object")
    void testContainsFalse() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Red");
        colors.addLast("Orange");

        assertFalse(colors.contains("Yellow"), "The list should not contain Yellow color.");
    }

    @Test
    @DisplayName("Show all of the elements")
    void testToString() {
        CustomLinkedList<String> colors = new CustomLinkedList<>();
        colors.addLast("Red");
        colors.addLast("Orange");

        assertEquals("[Red, Orange]", colors.toString(), "The text should be: [Red, Orange].");
    }
}
