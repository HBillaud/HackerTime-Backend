package com.example.hackertimebackend.WebSocket;

import com.example.hackertimebackend.OTStuff.Changes;
import com.example.hackertimebackend.OTStuff.OT;
import com.example.hackertimebackend.OTStuff.RowChanges;
import com.example.hackertimebackend.WebSocketData.InterviewRoomSetting;
import com.example.hackertimebackend.WebSocketData.WebSocketMessage;
import com.example.hackertimebackend.WebSocketData.WebSocketGlobalData;

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
    public RowChanges greeting(@DestinationVariable String room, RowChanges old_change) throws Exception {
        if (!WebSocketGlobalData.Room_mapper.containsKey(room)) {
            System.out.println("Room " + room + " does not exist!");
            return null;
        }
        InterviewRoomSetting shared_room = WebSocketGlobalData.AllRooms.get(WebSocketGlobalData.Room_mapper.get(room));
        System.out.println("request is from " + old_change.from_user);
        if (old_change.from_user.equals("recruiter")) {
            return shared_room.ot_room.recruiter_send_update(old_change);
        } else if (old_change.from_user.equals("interviewee")) {
            return shared_room.ot_room.interviewee_send_update(old_change);
        }
        return null;
    }

    @MessageMapping("/001")
    @SendTo("/topic/001")
    public String sendChange(String code) throws Exception {
        System.out.println("CHECKING");
        return code;
    }
}
