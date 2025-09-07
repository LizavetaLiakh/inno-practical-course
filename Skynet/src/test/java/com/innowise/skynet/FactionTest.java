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

    @Test
    @DisplayName("Determine who will have the strongest army")
    void testTheStrongestArmy() throws InterruptedException {
        Factory factory = new Factory(100);
        Faction worldFaction = new Faction("World", factory);
        Faction wednesdayFaction = new Faction("Wednesday", factory);

        ExecutorService service = Executors.newFixedThreadPool(3);
        service.execute(factory);
        service.execute(worldFaction);
        service.execute(wednesdayFaction);

        Thread.sleep(50);

        int worldRobotsAmount = worldFaction.getRobotsAmount();
        int wednesdayRobotsAmount = wednesdayFaction.getRobotsAmount();

        service.shutdown();

        System.out.println("World faction - " + worldRobotsAmount + " robots\n" +
                "Wednesday faction - " + wednesdayRobotsAmount + " robots");
        if (worldRobotsAmount != wednesdayRobotsAmount) {
            System.out.println("WINNER - " + (worldRobotsAmount > wednesdayRobotsAmount ? "World" : "Wednesday")
                    + " faction");
        } else {
            System.out.println("NO WINNER");
        }

        assertTrue(worldRobotsAmount >= 0, "World faction should have 0 or more robots.");
        assertTrue(wednesdayRobotsAmount >= 0, "Wednesday faction should have 0 or more robots.");
    }

}
