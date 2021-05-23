package com.example.conductor_app;

public class Courses {

    private String busnumber;
    private String name;
    private String fromlocation;
    private String tolocation;
    private String busLocation;
    public Courses() {
        // empty constructor
        // required for Firebase.
    }

    // Constructor for all variables.
    public Courses(String name, String tolocation, String fromlocation,String busnumber,String busLocation) {
        this.name = name;
        this.tolocation = tolocation;
        this.fromlocation = fromlocation;
        this.busnumber = busnumber;
        this.busLocation = busLocation;
    }

    public String getBusnumber() {
        return busnumber;
    }

    public void setBusnumber(String busnumber) {
        this.busnumber = busnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFromlocation() {
        return fromlocation;
    }

    public void setFromlocation(String fromlocation) {
        this.fromlocation = fromlocation;
    }

    public String getTolocation() {
        return tolocation;
    }

    public void setTolocation(String tolocation) {
        this.tolocation = tolocation;
    }

    public String getBusLocation() {
        return busLocation;
    }

    public void setBusLocation(String busLocation) {
        this.busLocation = busLocation;
    }
}
