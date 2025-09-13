package com.innowise.skynet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Main test")
public class RunnerTest {

    @Test
    @DisplayName("Determine who will have the strongest army")
    void testDetermineTheStrongestArmy() throws InterruptedException {
        int days = 100;
        Runner runner = new Runner(days);
        runner.startThreads();

        int worldRobotsAmount = runner.getWorldFaction().getRobotsAmount();
        int worldRobotsPossible = runner.getWorldFaction().getNextRobotsPossible();
        int wednesdayRobotsAmount = runner.getWednesdayFaction().getRobotsAmount();
        int wednesdayRobotsPossible = runner.getWednesdayFaction().getNextRobotsPossible();
        System.out.println("World faction - " + worldRobotsAmount + " robots and " + worldRobotsPossible +
                " next possible robot(-s)\nWednesday faction - " + wednesdayRobotsAmount + " robots and " +
                wednesdayRobotsPossible + " next possible robot(-s)\nWINNER - " + runner.getWinner() + " faction");

        assertTrue(worldRobotsAmount >= 0, "World faction should have 0 or more robots.");
        assertTrue(wednesdayRobotsAmount >= 0, "Wednesday faction should have 0 or more robots.");
        assertEquals(days, runner.getFactory().getDayCounter(), "The amount of working days should be " + days);
    }

}
