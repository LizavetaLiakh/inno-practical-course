package com.innowise.skynet;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Faction {

    private String title;
    private ArrayList<Robot> robots;
    private ArrayList<RobotPart> parts;

    public Faction(String title) {
        this.title = title;
        this.parts = new ArrayList<>();
    }

    public void getPartsFromFactory(LocalDateTime dateTime, Factory factory) {
        if (dateTime.getHour() < 6) {
            for (int i = 0; i < 5; i++) {
                parts.add(factory.getParts().getFirst());
            }
        }
    }
}
