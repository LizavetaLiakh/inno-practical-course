package com.innowise.skynet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class Factory implements Runnable {

    private LinkedList<RobotPart> parts = new LinkedList<>();
    private volatile boolean isRunning = true;
    private LocalDateTime currentTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
    private boolean isNight = false;

    private int dayCounter = 0;
    private final int DAYS_TOTAL;

    /**
     *
     * @param days amount of days factory should work
     */
    public Factory(int days) {
        this.DAYS_TOTAL = days;
    }

    /**
     *
     * @param partsAmount amount of parts to steal
     * @return list of different stolen robot parts
     */
    public synchronized List<RobotPart> stealParts(int partsAmount) {
        List<RobotPart> stolenParts = new LinkedList<RobotPart>();
        for (int i = 0; i < partsAmount && !parts.isEmpty(); i++) {
            stolenParts.add(parts.removeFirst());
        }
        return stolenParts;
    }

    /**
     * Produces parts once a day
     */
    public synchronized void produceParts() {
        for (int i = 0; i < 10; i++) {
            parts.add(RobotPart.getRandomPart());
        }
    }

    /**
     *
     * @return list of all produced robot parts
     */
    public LinkedList<RobotPart> getParts() {
        return parts;
    }

    @Override
    public void run() {
        while (isRunning && dayCounter < DAYS_TOTAL) {
            synchronized (this) {
                currentTime = currentTime.with(LocalTime.of(10, 0));
                isNight = false;
                produceParts();
                notifyAll();
            }

            synchronized (this) {
                currentTime = currentTime.plusDays(1).with(LocalTime.MIDNIGHT);
                dayCounter++;
                isNight = true;
                notifyAll();
            }
        }

        isRunning = false;

        synchronized (this) {
            notifyAll();
        }
    }

    /**
     *
     * @return true if thread is running
     */
    public synchronized boolean isRunning() {
        return isRunning;
    }

    /**
     *
     * @return true if it's night now
     */
    public boolean getIsNight() {
        return isNight;
    }

    /**
     *
     * @return amount of days when factory worked
     */
    public int getDayCounter() {
        return dayCounter;
    }

    @Override
    public String toString() {
        return "Factory. Robot parts: " + parts.size() + "\nThread is running: " + isRunning + "\nCurrent time: " +
                currentTime + "\nIs night: " + isNight + "\nDay counter: " + dayCounter + "\nDays total number: " +
                DAYS_TOTAL;
    }

}
