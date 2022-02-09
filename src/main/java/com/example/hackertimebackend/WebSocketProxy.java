package com.example.hackertimebackend;

import com.example.hackertimebackend.WebSocketData.WebSocketMessage;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketProxy {

    public WebSocketProxy() {
        System.out.println("Proxy Initialized!");
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public WebSocketMessage greeting(WebSocketMessage message) throws Exception {
        System.out.println("Message received: " + message.getContent());
        Thread.sleep(1000);
        return new WebSocketMessage("Hello, " + HtmlUtils.htmlEscape(message.getContent()));
    }
}
