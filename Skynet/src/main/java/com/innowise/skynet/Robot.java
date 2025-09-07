package com.innowise.skynet;

public class Robot {

    private final static int PAIR = 2;
    private RobotPart head;
    private RobotPart torso;
    private RobotPart[] hands = new RobotPart[PAIR];;
    private RobotPart[] feet = new RobotPart[PAIR];;

    public Robot() {}

    /**
     *
     * @param head head of the robot
     * @param torso torso of the robot
     * @param hands array with 2 hands of the robot
     * @param feet array with 2 feet of the robot
     */
    public Robot(RobotPart head, RobotPart torso, RobotPart[] hands, RobotPart[] feet) {
        setHead(head);
        setTorso(torso);
        setHands(hands);
        setFeet(feet);
    }

    /**
     *
     * @param head head of the robot
     */
    public void setHead(RobotPart head) {
        if (head == RobotPart.HEAD) {
            this.head = head;
        }
    }

    /**
     *
     * @param torso torso of the robot
     */
    public void setTorso(RobotPart torso) {
        if (torso == RobotPart.TORSO) {
            this.torso = torso;
        }
    }

    /**
     *
     * @param hands array with 2 hands of the robot
     */
    public void setHands(RobotPart[] hands) {
        for (int i = 0; i < PAIR && i < hands.length; i++) {
            if (hands[i] == RobotPart.HAND) {
                this.hands[i] = hands[i];
            }
        }
    }

    /**
     *
     * @param feet array with 2 feet of the robot
     */
    public void setFeet(RobotPart[] feet) {
        for (int i = 0; i < PAIR && i < feet.length; i++) {
            if (feet[i] == RobotPart.FEET) {
                this.feet[i] = feet[i];
            }
        }
    }

    public RobotPart getHead() {
        return head;
    }

    public RobotPart getTorso() {
        return torso;
    }

    public RobotPart[] getHands() {
        return hands;
    }

    public RobotPart[] getFeet() {
        return feet;
    }
}
