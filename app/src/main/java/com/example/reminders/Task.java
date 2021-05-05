package com.example.reminders;

public class Task {
    String name, date, time, type;

    public Task() {
        // for Firestore
    }

    public Task(String name, String type){
        this.name = name;
        this.type = type;
    }

    public Task(String name, String type, String date, String time){
        this.name = name;
        this.type = type;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }
}
