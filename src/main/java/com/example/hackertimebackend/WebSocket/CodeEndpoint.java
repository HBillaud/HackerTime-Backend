package com.example.hackertimebackend.WebSocket;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/code/{roomId}")
public class CodeEndpoint {

    @OnOpen
    public void onOpen(@PathParam("roomId") String roomId, Session session) throws IOException {
        // Get session and WebSocket connection
        System.out.println("CONNECTED");
    }

    @OnMessage
    public void onMessage(Session session) throws IOException {
        // Handle new messages
    }


    @OnClose
    public void onClose(Session session) throws IOException {
        // WebSocket connection closes
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
}
