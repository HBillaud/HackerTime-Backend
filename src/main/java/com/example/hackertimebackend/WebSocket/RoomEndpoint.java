package com.example.hackertimebackend.WebSocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.example.hackertimebackend.OTStuff.OT;
import com.example.hackertimebackend.WebSocketData.InterviewRoomSetting;
import com.example.hackertimebackend.WebSocketData.Interviewer;
import com.example.hackertimebackend.WebSocketData.WebSocketGlobalData;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomEndpoint {

    public String CodeGenerator(int size) {
        int leftLimit = 65; // letter 'A'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int randomInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            if (randomInt >= 91 && randomInt <= 96) {
                i--;
                continue;
            }
            buffer.append((char) randomInt);
        }
        return buffer.toString();
    }
    
    @PostMapping("/hostroom")
    public String create_room(@RequestBody Interviewer empName) {
        String newCode = CodeGenerator(30);
        InterviewRoomSetting newRoom = new InterviewRoomSetting();
        newRoom.RoomCode = newCode;
        WebSocketGlobalData.Room_mapper.put(newCode, WebSocketGlobalData.AllRooms.size());
        WebSocketGlobalData.AllRooms.add(newRoom);
        return newCode;
    }
}
