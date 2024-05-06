package com.example.chumbatelegramm.model.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lesson implements Comparable<Lesson>{

    private final String date;
    private final String name;
    private final String timeStart;
    private final String timeEnd;
    private final String roomCode;

    public Lesson(String dateStr, String name, String timeStart, String timeEnd, String roomCode) throws ParseException {
        date = dateStr.split("T")[0];
        this.name = name;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.roomCode = roomCode;
    }

    @Override
    public String toString() {
        return  name + '\n'
                + timeStart +" - "+timeEnd + '\n'
                + roomCode + '\n';
    }

    public String getDate() {
        return date;
    }

    @Override
    public int compareTo(Lesson o) {
        Date date1 = null;
        Date date2 = null;
        try {
             date1 = new SimpleDateFormat("yyyy-dd-MM").parse(getDate());
             date2 = new SimpleDateFormat("yyyy-dd-MM").parse(o.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return date1.compareTo(date2);
    }
}
