package com.example.hackertimebackend.WebSocketData;

public class WebSocketMessage {
    private String content;


    public WebSocketMessage() {
    }

    public WebSocketMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public WebSocketMessage content(String content) {
        setContent(content);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " content='" + getContent() + "'" +
            "}";
    }

}
