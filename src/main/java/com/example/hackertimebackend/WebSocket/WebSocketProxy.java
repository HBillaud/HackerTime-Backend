package com.example.hackertimebackend.WebSocket;

import com.example.hackertimebackend.WebSocketData.WebSocketMessage;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketProxy {

    public WebSocketProxy() {
        System.out.println("Proxy Initialized!");
    }

    @MessageMapping("/{room}")
    @SendTo("/topic/{room}")
    public WebSocketMessage greeting(@DestinationVariable String room, WebSocketMessage message) throws Exception {
        System.out.println("Room created: " + room);
        System.out.println("Message received: " + message.getContent());
        System.out.println("User is: " + message.getUser());
        return new WebSocketMessage(message.getUser(), message.getContent());
    }
}
