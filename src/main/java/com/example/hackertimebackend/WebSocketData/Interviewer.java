package com.example.hackertimebackend.WebSocketData;

public class Interviewer {
    private String name;

    public Interviewer() {
    }

    public Interviewer(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Interviewer name(String name) {
        setName(name);
        return this;
    }
}
