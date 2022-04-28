package com.example.hackertimebackend.WebSocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.example.hackertimebackend.WebSocketData.InterviewRoomSetting;
import com.example.hackertimebackend.WebSocketData.WebSocketGlobalData;
import com.example.hackertimebackend.commons.CreateReport;
import com.example.hackertimebackend.compiler.compileFile;

import com.example.hackertimebackend.compiler.compileService;
import com.example.hackertimebackend.db.models.Report;
import com.example.hackertimebackend.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RoomEndpoint {
    @Autowired
    private ReportService reportService;
    @Autowired
    private compileService compile;

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
    public ResponseEntity create_room(@RequestBody @Valid RoomRequest request) throws Exception {
        try {
            String newCode = CodeGenerator(30);
            InterviewRoomSetting newRoom = new InterviewRoomSetting();
            newRoom.RoomCode = newCode;
            WebSocketGlobalData.Room_mapper.put(newCode, WebSocketGlobalData.AllRooms.size());
            WebSocketGlobalData.AllRooms.add(newRoom);
            RoomResponse room = new RoomResponse(request.getQuestion(), newCode);
            Report report = reportService.generateReport(new CreateReport(request.getQuestion(), newCode));
            ResponseEntity response = new ResponseEntity(room, HttpStatus.CREATED);
            return response;
        } catch (Exception e) {
            throw new Exception("Failed to create room!");
        }
    }

    @CrossOrigin
    @PostMapping("/getCode")
    public Map<String, String> compile_code(@RequestBody CodeStruct code) throws IOException {
        return compile.compile(code);
    }
}
