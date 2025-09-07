package com.innowise.skynet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Robot Tests")
public class RobotTest {

    @Test
    @DisplayName("Set robot's head")
    void testSetHead() {
        RobotPart head = RobotPart.HEAD;

        Robot robot = new Robot();
        robot.setHead(head);

        assertEquals(RobotPart.HEAD, robot.getHead(), "Robot should have a head.");
    }

    @Test
    @DisplayName("Set robot's torso")
    void testSetTorso() {
        RobotPart torso = RobotPart.TORSO;

        Robot robot = new Robot();
        robot.setTorso(torso);

        assertEquals(RobotPart.TORSO, robot.getTorso(), "Robot should have a torso.");
    }

    @Test
    @DisplayName("Set robot's hands")
    void testSetHands() {
        RobotPart hand = RobotPart.HAND;
        RobotPart[] hands = {hand, hand};

        Robot robot = new Robot();
        robot.setHands(hands);

        assertEquals(RobotPart.HAND, robot.getHands()[0], "Robot should have a left hand.");
        assertEquals(RobotPart.HAND, robot.getHands()[1], "Robot should have a right hand.");
    }

    @Test
    @DisplayName("Set robot's feet")
    void testSetFeet() {
        RobotPart foot = RobotPart.FEET;
        RobotPart[] feet = {foot, foot};

        Robot robot = new Robot();
        robot.setFeet(feet);

        assertEquals(RobotPart.FEET, robot.getFeet()[0], "Robot should have a left foot.");
        assertEquals(RobotPart.FEET, robot.getFeet()[1], "Robot should have a right foot.");
    }

    @Test
    @DisplayName("Set robot's torso instead of head")
    void testSetTorsoInsteadOfHead() {
        RobotPart torso = RobotPart.TORSO;

        Robot robot = new Robot();
        robot.setHead(torso);

        assertNull(robot.getHead(), "It's impossible to set torso instead of head.");
    }

}
