package com.innowise.skynet;

public class Robot {

    private RobotPart head;
    private RobotPart torso;
    private RobotPart[] hands;
    private RobotPart[] feet;

    public Robot(RobotPart head, RobotPart torso, RobotPart[] hands, RobotPart[] feet) {
        setHead(head);
        setTorso(torso);
        setHands(hands);
        setFeet(feet);
    }

    public void setHead(RobotPart head) {
        if (head == RobotPart.HEAD) {
            this.head = head;
        }
    }

    public void setTorso(RobotPart torso) {
        if (torso == RobotPart.TORSO) {
            this.torso = torso;
        }
    }

    public void setHands(RobotPart[] hands) {
        for (int i = 0; i < 2 && i < hands.length; i++) {
            if (hands[i] == RobotPart.HAND) {
                this.hands[i] = hands[i];
            }
        }
    }

    public void setFeet(RobotPart[] feet) {
        for (int i = 0; i < 2 && i < feet.length; i++) {
            if (feet[i] == RobotPart.FEET) {
                this.feet[i] = feet[i];
            }
        }
    }
}
