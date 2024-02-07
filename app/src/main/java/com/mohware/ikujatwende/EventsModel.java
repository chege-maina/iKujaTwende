package com.mohware.ikujatwende;

public class EventsModel {
    private String name;
    private String location;
    private String reporter;
    private String Time;
    private String date;
    private String Path;
    private String Desc;

    public EventsModel(String name, String location, String reporter, String time, String date, String path, String desc) {
        this.name = name;
        this.location = location;
        this.reporter = reporter;
        Time = time;
        this.date = date;
        Path = path;
        Desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }
}
