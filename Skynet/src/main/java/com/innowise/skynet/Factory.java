package com.innowise.skynet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents a factory that produces robot parts during the daytime.
 */
public class Factory implements Runnable {

    private LinkedList<RobotPart> parts = new LinkedList<>();
    private LinkedList<Faction> factions = new LinkedList<>();
    private volatile boolean isRunning = true;
    private LocalDateTime currentTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
    private boolean isNight = false;

    private int dayCounter = 0;
    private final int DAYS_TOTAL;
    private final static int PRODUCE_PLAN = 10;

    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Constructs a factory.
     *
     * @param days Amount of days factory should work.
     */
    public Factory(int days) {
        this.DAYS_TOTAL = days;
    }

    /**
     * Adds a new {@code Faction} to steal parts from current factory.
     *
     * @param faction {@code Faction} that will steal parts from the {@code Factory}.
     */
    public void addFaction(Faction faction) {
        factions.add(faction);
    }

    /**
     * Creates a list of {@code RobotPart} for every faction that is linked with the {@code Factory}.
     *
     * @param partsAmount Amount of {@code RobotPart} to steal.
     * @return List of different stolen {@code RobotPart}.
     */
    public List<RobotPart> stealParts(int partsAmount) {
        lock.lock();
        try {
            List<RobotPart> stolenParts = new LinkedList<>();
            for (int i = 0; i < partsAmount && !parts.isEmpty(); i++) {
                stolenParts.add(parts.removeFirst());
            }
            return stolenParts;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Produces 10 {@code RobotPart} once a day.
     */
    public synchronized void produceParts() {
        for (int i = 0; i < PRODUCE_PLAN; i++) {
            parts.add(RobotPart.getRandomPart());
        }
    }

    /**
     * Returns a copy of the list of {@code RobotPart}.
     *
     * @return Copy of the list with all produced {@code RobotPart}.
     */
    public LinkedList<RobotPart> getPartsCopy() {
        return new LinkedList<>(parts);
    }

    @Override
    public void run() {
        while (dayCounter < DAYS_TOTAL) {
            synchronized (this) {
                currentTime = currentTime.with(LocalTime.of(10, 0));
                isNight = false;
                produceParts();
                notifyAll();

                waitSomeTime();
            }

            synchronized (this) {
                currentTime = currentTime.plusDays(1).with(LocalTime.MIDNIGHT);
                isNight = true;
                notifyAll();

                waitSomeTime();
                dayCounter++;
            }
        }

        synchronized (this) {
            isRunning = false;
            notifyAll();
        }
    }

    /**
     * Makes factory wait some time at night/day.
     */
    private void waitSomeTime() {
        try {
            wait(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Returns true if the {@code Factory} is running.
     *
     * @return True if the thread is running.
     */
    public synchronized boolean isRunning() {
        return isRunning;
    }

    /**
     * Returns true if it's night now.
     *
     * @return True if it's night now.
     */
    public synchronized boolean getIsNight() {
        return isNight;
    }

    /**
     * Returns the amount of working days.
     *
     * @return Amount of days when the {@code Factory} worked.
     */
    public synchronized int getDayCounter() {
        return dayCounter;
    }

    @Override
    public String toString() {
        return "Factory. Robot parts: " + parts.size() + "\nThread is running: " + isRunning + "\nCurrent time: " +
                currentTime + "\nIs night: " + isNight + "\nDay counter: " + dayCounter + "\nDays total number: " +
                DAYS_TOTAL;
    }

    public synchronized int getPartsCount() {
        return parts.size();
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public int getDaysTotal() {
        return DAYS_TOTAL;
    }
}
