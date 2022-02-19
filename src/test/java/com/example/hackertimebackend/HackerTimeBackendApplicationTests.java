package com.example.hackertimebackend;

import com.example.hackertimebackend.OTStuff.OT;
import com.example.hackertimebackend.OTStuff.OTUser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.hackertimebackend.OTStuff.Changes;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@AutoConfigureMockMvc
class HackerTimeBackendApplicationTests {

    OT ot_room;
    OTUser interviewer;
    OTUser interviewee;

    @BeforeEach
    public void initialize() {
        ot_room = new OT();
        interviewer = new OTUser();
        interviewee = new OTUser();
        interviewee.setCursor(0);
        interviewer.setCursor(0);
        ot_room.recruiter = interviewer;
        ot_room.interviewee = interviewee;
        ot_room.common_context = "";
    }

    @Test
    void contextLoads() {
    }

    @Test
    void hello() {
        System.out.println("Hello World");
    }

    @Test
    void add_from_Interviewer_initial_add() {
        Changes old_change = new Changes();
        old_change.Init_index = 0;
        old_change.change_type = "add";
        old_change.context = "hello";
        Changes new_change = ot_room.recruiter_send_update(old_change);
        assertTrue(ot_room.common_context.equals("hello"));
        assertTrue(new_change.cursor_move == 5);
    }

    @Test
    void add_from_Interviewer_cursor_change() {
        ot_room.common_context = "hello";
        interviewee.setCursor(5);
        Changes old_change = new Changes();
        old_change.Init_index = 0;
        old_change.change_type = "add";
        old_change.context = "Not ";
        Changes new_change = ot_room.recruiter_send_update(old_change);
        assertTrue(ot_room.common_context.equals("Not hello"));
        assertTrue(new_change.cursor_move == 4);
    }

    @Test
    void add_from_Interviewer_no_cursor_change() {
        ot_room.common_context = "hello";
        interviewer.setCursor(5);
        Changes old_change = new Changes();
        old_change.Init_index = 5;
        old_change.change_type = "add";
        old_change.context = " World";
        Changes new_change = ot_room.recruiter_send_update(old_change);
        assertTrue(ot_room.common_context.equals("hello World"));
        assertTrue(new_change.cursor_move == 0);
    }

}
