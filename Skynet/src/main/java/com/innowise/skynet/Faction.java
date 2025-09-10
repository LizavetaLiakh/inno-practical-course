package com.innowise.skynet;

import java.util.LinkedList;
import java.util.List;

public class Faction implements Runnable, Comparable<Faction> {

    private final static int PARTS_AMOUNT = 5;
    private final static int PAIR = 2;

    private String title;
    private Factory factory;
    private LinkedList<Robot> robots = new LinkedList<>();

    private LinkedList<RobotPart> headParts = new LinkedList<>();
    private LinkedList<RobotPart> torsoParts = new LinkedList<>();
    private LinkedList<RobotPart> handParts = new LinkedList<>();
    private LinkedList<RobotPart> footParts = new LinkedList<>();

    /**
     * Constructs a faction.
     *
     * @param title title of the faction
     * @param factory factory to steal robot parts from
     */
    public Faction(String title, Factory factory) {
        this.title = title;
        this.factory = factory;
    }

    /**
     * Makes a robot from stolen head, torso, 2 hands and 2 feet
     */
    public void makeRobot() {
        while (!headParts.isEmpty() && ! torsoParts.isEmpty()
                && handParts.size() >= PAIR && footParts.size() >= PAIR) {
            Robot robot = new Robot();
            robot.setHead(takePart(headParts));
            robot.setTorso(takePart(torsoParts));
            robot.setHands(new RobotPart[] {takePart(handParts), takePart(handParts)});
            robot.setFeet(new RobotPart[] {takePart(footParts), takePart(footParts)});
            robots.add(robot);
        }
    }

    /**
     * Takes the first robot part of the list and deletes it.
     *
     * @param parts list of robot parts
     * @return random robot part
     */
    private RobotPart takePart(LinkedList<RobotPart> parts) {
        return parts.removeFirst();
    }

    @Override
    public void run() {
        try {
            while (factory.isRunning()) {
                synchronized (factory) {
                    while (!factory.getIsNight() && factory.isRunning()) {
                        factory.wait();
                    }
                    if (!factory.isRunning()) {
                        break;
                    }

                    stealPartsFromFactory();
                    factory.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public int getPartsAmount() {
        return headParts.size() + torsoParts.size() + handParts.size() + footParts.size();
    }

    public int getRobotsAmount() {
        return robots.size();
    }

    public String getTitle() {
        return title;
    }

    /**
     * Test function without threads
     */
    public void stealPartsFromFactory() {
        List<RobotPart> stolenParts = factory.stealParts(PARTS_AMOUNT);
        for (RobotPart part : stolenParts) {
            switch (part) {
                case HEAD -> headParts.add(part);
                case TORSO -> torsoParts.add(part);
                case HAND -> handParts.add(part);
                case FEET -> footParts.add(part);
            }
        }
        makeRobot();
    }

    /**
     * Counts how many robots it's possible to create from remaining parts in the nearest time.
     *
     * @return amount of possible robots to create
     */
    public int getNextRobotsPossible() {
        return Math.min(Math.min(headParts.size(), torsoParts.size()), Math.min(handParts.size(), footParts.size()));
    }

    /**
     * Compares 2 factions.
     *
     * @param otherFaction the object to be compared.
     * @return true if the otherFaction is the same as the current faction
     */
    @Override
    public int compareTo(Faction otherFaction) {
        return Integer.compare(this.getPartsAmount(), otherFaction.getPartsAmount());
    }

    @Override
    public String toString() {
        return "Faction " + title + "\nFactory: " + factory + "\nRobots amount: " + getRobotsAmount() + "\nHead parts: "
                + headParts.size() + "\nTorso parts: " + torsoParts.size() + "\nHand parts: " + handParts.size() +
                "\nFoot parts: " + footParts.size();
    }
}
