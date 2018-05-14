package me.junbing.hci;

public class Bus {
    String time;
    String departure_loc;
    String direction;

    Boolean priority;

    Boolean selected;

    public Bus() {
    }

    public Bus(String time, String departure_loc, String direction, Boolean priority, Boolean selected) {
        this.time = time;
        this.departure_loc = departure_loc;
        this.direction = direction;
        this.priority = priority;
        this.selected = selected;
    }
}
