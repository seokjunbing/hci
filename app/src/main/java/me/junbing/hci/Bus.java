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

    private static String getDirectionStr(String directionFrom, String directionTo) {
        return directionFrom.concat(" -> ").concat(directionTo);
    }

    static ArrayList<Bus> getBusArrayList(String date, String departure_loc, String directionFrom, String directionTo) {
        ArrayList<Bus> buses = new ArrayList<>();

        for(String t: times) {
            buses.add(new Bus(date, t, departure_loc, getDirectionStr(directionFrom, directionTo), Boolean.FALSE, Boolean.FALSE));
        }
        return buses;
    }

    static ArrayList<Bus> getSelectBusArrayList(String date, String departure_loc, String directionFrom, String directionTo, int[] busChoices, Boolean[] priorities) {
        ArrayList<Bus> buses = new ArrayList<>();

        for(int ind = 0; ind < busChoices.length; ind++) {
            buses.add(new Bus(date, times[ind], departure_loc, getDirectionStr(directionFrom, directionTo), priorities[ind], Boolean.FALSE));
        }
        return buses;
    }

    static Bus getSelectBus(String date, String departure_loc, String directionFrom, String directionTo, int busChoice, Boolean priority) {
        return new Bus(date, times[busChoice], departure_loc, getDirectionStr(directionFrom, directionTo), priority, Boolean.FALSE);
    }

}
