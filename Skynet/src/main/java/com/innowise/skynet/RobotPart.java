package com.innowise.skynet;

import java.util.concurrent.ThreadLocalRandom;

public enum RobotPart {
    HEAD, TORSO, HAND, FEET;

    private static final RobotPart[] VALUES = values();

    public static RobotPart getRandomPart() {
        return VALUES[ThreadLocalRandom.current().nextInt(VALUES.length)];
    }
}
