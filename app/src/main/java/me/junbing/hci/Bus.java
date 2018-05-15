package me.junbing.hci;

import java.util.ArrayList;

public class Bus {
    String time;
    String departure_loc;
    String direction;
    String date;
    Boolean priority;
    Boolean selected;

    static String[] times = {"8AM - 11AM", "10AM - 1PM", "12PM - 3PM", "2PM - 5PM", "4PM - 7PM"};

    public Bus() {
    }

    public Bus(String date, String time, String departure_loc, String direction, Boolean priority, Boolean selected) {
        this.time = time;
        this.date = date;
        this.departure_loc = departure_loc;
        this.direction = direction;
        this.priority = priority;
        this.selected = selected;
    }

    static ArrayList<Bus> getBusArrayList(String date, String departure_loc, String direction) {
        ArrayList<Bus> buses = new ArrayList<>();

        for(String t: times) {
            buses.add(new Bus(date, t, departure_loc, direction, Boolean.FALSE, Boolean.FALSE));
        }

//        buses.add(new Bus(date, "8AM - 11AM", departure_loc, direction, Boolean.FALSE, Boolean.FALSE));
//        buses.add(new Bus(date, "10AM - 1PM", departure_loc, direction, Boolean.FALSE, Boolean.FALSE));
//        buses.add(new Bus(date, "12PM - 3PM", departure_loc, direction, Boolean.FALSE, Boolean.FALSE));
//        buses.add(new Bus(date, "2PM - 5PM", departure_loc, direction, Boolean.FALSE, Boolean.FALSE));
//        buses.add(new Bus(date, "4PM - 7PM", departure_loc, direction, Boolean.FALSE, Boolean.FALSE));
        return buses;
    }

    static ArrayList<Bus> getSelectBusArrayList(String date, String departure_loc, String direction, int[] choices, Boolean[] priorities) {
        ArrayList<Bus> buses = new ArrayList<>();

        for(int ind = 0; ind < choices.length; ind++) {
            buses.add(new Bus(date, times[ind], departure_loc, direction, priorities[ind], Boolean.FALSE));
        }
        return buses;
    }

}
