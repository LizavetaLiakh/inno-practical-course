package com.innowise.skynet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class responsible for creating and controlling threads
 */
public class Runner {
    private final int days;
    private Factory factory;
    private Faction worldFaction;
    private Faction wednesdayFaction;

    /**
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

        Thread.sleep(50);

        service.shutdown();
    }

    /**
     *
     * @return factory with most robots
     */
    public String getWinner() {
        int worldRobotsAmount = worldFaction.getRobotsAmount();
        int wednesdayRobotsAmount = wednesdayFaction.getRobotsAmount();

        if (worldRobotsAmount > wednesdayRobotsAmount) {
            return "World";
        } else if (wednesdayRobotsAmount > worldRobotsAmount) {
            return "Wednesday";
        } else {
            return "NO WINNER";
        }
    }

    /**
     *
     * @return World faction
     */
    public Faction getWorldFaction() {
        return worldFaction;
    }

    /**
     *
     * @return Wednesday faction
     */
    public Faction getWednesdayFaction() {
        return wednesdayFaction;
    }

    /**
     *
     * @return factory
     */
    public Factory getFactory() {
        return factory;
    }

    @Override
    public String toString() {
        return "Days total: " + days + "\nFactory: " + factory + "\nWorld faction: " + worldFaction + "\nWendesday " +
                "faction: " + wednesdayFaction;
    }
}
