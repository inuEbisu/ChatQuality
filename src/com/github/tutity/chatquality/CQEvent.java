package com.github.tutity.chatquality;

import java.util.List;

public class CQEvent {

    public double MaxPoint;
    public double MinPoint;
    public List<String> Commands;

    public CQEvent(double minpoint, double maxpoint, List<String> cmd) {
        MaxPoint = maxpoint;
        MinPoint = minpoint;
        Commands = cmd;
    }
}
