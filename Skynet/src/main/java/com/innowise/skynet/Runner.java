package com.innowise.skynet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Class responsible for creating and controlling threads
 */
public class Runner {
    private final int days;
    private Factory factory;
    private Faction worldFaction;
    private Faction wednesdayFaction;

    /**
     * Constructs a runner.
     *
     * @param days amount of days when factory should work
     */
    public Runner(int days) {
        this.days = days;
    }


    /**
     * Start multithreading with factory, World faction and Wednesday faction
     */
    public void startThreads() throws InterruptedException {
        factory = new Factory(days);
        worldFaction = new Faction("World", factory);
        wednesdayFaction = new Faction("Wednesday", factory);

        ExecutorService service = Executors.newFixedThreadPool(3);
        service.execute(factory);
        service.execute(worldFaction);
        service.execute(wednesdayFaction);

        service.shutdown();
        if (!service.awaitTermination(60, TimeUnit.SECONDS)) {
            service.shutdown();
        }
    }

    /**
     * Gets a winner of factions that has most robots and possible robots.
     *
     * @return factory with most robots
     */
    public String getWinner() {
        int worldRobotsAmount = worldFaction.getRobotsAmount() + worldFaction.getNextRobotsPossible();
        int wednesdayRobotsAmount = wednesdayFaction.getRobotsAmount() + wednesdayFaction.getNextRobotsPossible();

        if (worldRobotsAmount > wednesdayRobotsAmount) {
            return "World";
        } else if (wednesdayRobotsAmount > worldRobotsAmount) {
            return "Wednesday";
        } else {
            return "NO WINNER";
        }
    }

    public Faction getWorldFaction() {
        return worldFaction;
    }

    public Faction getWednesdayFaction() {
        return wednesdayFaction;
    }

    public Factory getFactory() {
        return factory;
    }

    @Override
    public String toString() {
        return "Days total: " + days + "\nFactory: " + factory + "\nWorld faction: " + worldFaction + "\nWendesday " +
                "faction: " + wednesdayFaction;
    }
}
