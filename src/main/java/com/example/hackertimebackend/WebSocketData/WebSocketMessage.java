package com.example.hackertimebackend.WebSocketData;

public class WebSocketMessage {
    private String user;
    private String content;

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public WebSocketMessage(String user, String content) {
        this.user = user;
        this.content = content;
    }
}

    
