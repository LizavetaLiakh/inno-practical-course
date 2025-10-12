package com.innowise.skynet;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a robot that is made of different {@code RobotPart} objects.
 */
@AllArgsConstructor
@Getter
public class Robot {

    private static final int PAIR = 2;
    private RobotPart head;
    private RobotPart torso;
    private RobotPart[] hands = new RobotPart[PAIR];;
    private RobotPart[] feet = new RobotPart[PAIR];;

    public Robot() {}

    /**
     * Sets a head of the robot.
     *
     * @param head Head of the robot.
     */
    public void setHead(RobotPart head) {
        if (head == RobotPart.HEAD) {
            this.head = head;
        }
    }

    /**
     * Sets a torso of the robot.
     *
     * @param torso Torso of the robot.
     */
    public void setTorso(RobotPart torso) {
        if (torso == RobotPart.TORSO) {
            this.torso = torso;
        }
    }

    /**
     * Sets a pair of hands of the robot.
     *
     * @param hands Array with 2 hands of the robot.
     */
    public void setHands(RobotPart[] hands) {
        for (int i = 0; i < PAIR && i < hands.length; i++) {
            if (hands[i] == RobotPart.HAND) {
                this.hands[i] = hands[i];
            }
        }
    }

    /**
     * Sets a pair of feet of the robot.
     *
     * @param feet Array with 2 feet of the robot.
     */
    public void setFeet(RobotPart[] feet) {
        for (int i = 0; i < PAIR && i < feet.length; i++) {
            if (feet[i] == RobotPart.FEET) {
                this.feet[i] = feet[i];
            }
        }
    }
}
