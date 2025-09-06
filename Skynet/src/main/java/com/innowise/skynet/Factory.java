package com.innowise.skynet;

import java.time.LocalDate;
import java.util.ArrayList;

public class Factory {

    private ArrayList<RobotPart> parts;
    private LocalDate lastProducingDate;

    public Factory() {
        this.parts = new ArrayList<>();
        this.lastProducingDate = LocalDate.of(0,0,0);
    }

    public void produceParts(LocalDate date) {
        if (lastProducingDate.isBefore(date)) {
            for (int i = 0; i < 10; i++) {
                parts.add(RobotPart.getRandomPart());
            }
        }
    }

    public ArrayList<RobotPart> getParts() {
        return parts;
    }

}
