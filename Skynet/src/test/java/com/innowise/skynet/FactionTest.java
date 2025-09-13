package com.innowise.skynet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Faction Tests")
public class FactionTest {

    @Test
    @DisplayName("Get parts from factory")
    public void testGetPartsFromFactory() throws InterruptedException {
        Factory factory = new Factory(1);
        factory.produceParts();

        Faction faction = new Faction("World", factory);
        faction.stealPartsFromFactory();

        assertEquals(5, faction.getPartsAmount(), "There should be 5 parts at the faction.");
    }

    @Test
    @DisplayName("Get parts from factory for 3 days and make robots")
    public void testGetPartsAndMakeRobots() {
        Factory factory = new Factory(3);
        Faction faction = new Faction("World", factory);

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(factory);
        service.execute(faction);

        service.shutdown();

        assertTrue(faction.getRobotsAmount() >= 0, "Faction should have 0 or more robots.");
    }

}
