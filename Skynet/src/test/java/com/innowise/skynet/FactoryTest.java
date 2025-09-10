package com.innowise.skynet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Factory Tests")
public class FactoryTest {

    @Test
    @DisplayName("Produce 10 robot parts per day")
    void testProduceParts() {
        Factory factory = new Factory(1);
        Thread factoryThread = new Thread(factory);
        factoryThread.start();

        try {
            factoryThread.join();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        assertEquals(10, factory.getPartsCount(), "There should be 10 robot parts in the factory.");
    }

    @Test
    @DisplayName("Steal requested amount of parts")
    void testStealParts() {
        Factory factory = new Factory(1);
        for (int i = 0; i < 10; i++) {
            factory.stealParts(0);
        }

        List<RobotPart> stolenParts = factory.stealParts(5);
        assertTrue(stolenParts.size() <= 5, "Should steal maximum 5 parts.");
    }

    @Test
    @DisplayName("Reducing parts from the factory when stealing")
    void testStealPartsReducing() {
        Factory factory = new Factory(1);
        factory.produceParts();

        synchronized (factory) {
            for (int i = 0; i < 10; i++) {
                factory.stealParts(0);
            }

            int beforeStealing = factory.getPartsCount();
            factory.stealParts(5);
            int afterStealing = factory.getPartsCount();

            assertEquals(beforeStealing - 5, afterStealing
                    , "There should be less parts at the factory after stealing.");
        }
    }

}
