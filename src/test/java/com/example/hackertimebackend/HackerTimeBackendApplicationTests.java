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

    Changes list1[] = {
        new Changes("interviewer", 1, "add", 0, 1, "h", 1),
        new Changes("interviewer", 1, "add", 1, 2, "e", 1),
        new Changes("interviewer", 1, "add", 2, 1, "l", 1),
        new Changes("interviewer", 1, "add", 3, 1, "l", 1),
        new Changes("interviewer", 1, "add", 4, 1, "o", 1),
        new Changes("interviewer", 1, "add", 5, 1, " ", 1),
        new Changes("interviewer", 1, "add", 6, 1, "w", 1),
        new Changes("interviewer", 1, "add", 7, 1, "o", 1),
        new Changes("interviewer", 1, "add", 8, 1, "r", 1),
        new Changes("interviewer", 1, "add", 9, 1, "l", 1),
        new Changes("interviewer", 1, "add", 10, 1, "d", 1),
        new Changes("interviewer", 1, "delete", 10, 11, "d", 1),
        new Changes("interviewer", 1, "add", 10, 1, "d", 1)
    };

    Changes list2[] = {
        new Changes("interviewer", 1, "add", 0, 1, "h", 1),
        new Changes("interviewer", 1, "add", 1, 2, "e", 1),
        new Changes("interviewee", 1, "add", 2, 3, "l", 1),
        new Changes("interviewee", 1, "add", 3, 4, "o", 1),
    };

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
        assertTrue(ot_room.recruiter.getCursor() == 5);
        assertTrue(new_change.cursor_move == 5);
    }

    @Test
    void add_from_Interviewee_initial_add() {
        Changes old_change = new Changes();
        old_change.Init_index = 0;
        old_change.change_type = "add";
        old_change.context = "hello";
        Changes new_change = ot_room.interviewee_send_update(old_change);
        assertTrue(ot_room.interviewee.getCursor() == 5);
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

    @Test
    void delete_from_Interviewer_no_cursor_change() {
        ot_room.common_context = "hello world";
        interviewer.setCursor(11);
        interviewee.setCursor(0);
        Changes old_change = new Changes();
        old_change.Init_index = 5;
        old_change.End_index = 11;
        old_change.change_type = "delete";
        old_change.size = 6;
        Changes new_change = ot_room.recruiter_send_update(old_change);
        assertTrue(ot_room.common_context.equals("hello"));
        assertTrue(new_change.cursor_move == 0);
    }

    @Test
    void delete_from_Interviewer_cursor_change() {
        ot_room.common_context = "hello world";
        interviewee.setCursor(11);
        interviewer.setCursor(6);
        Changes old_change = new Changes();
        old_change.Init_index = 0;
        old_change.End_index = 6;
        old_change.change_type = "delete";
        old_change.size = 6;
        Changes new_change = ot_room.recruiter_send_update(old_change);
        assertTrue(ot_room.common_context.equals("world"));
        assertTrue(new_change.cursor_move == -6);
    }

    @Test
    void mixed_stress_test_1() {
        for(Changes change : list1) {
            if (change.from_user.equals("recruiter")) ot_room.recruiter_send_update(change);
            else ot_room.interviewee_send_update(change);
        }
        assertTrue(ot_room.common_context.equals("hello world"));
        assertTrue(ot_room.recruiter.getCursor() == 11);
    }

    @Test
    void mixed_stress_test_2() {
        for(Changes change : list2) {
            if (change.from_user.equals("recruiter")) ot_room.recruiter_send_update(change);
            else ot_room.interviewee_send_update(change);
        }
        assertTrue(ot_room.common_context.equals("helo"));
    }

    @Test
    void recruiter_replace_middle_decrease() {
        ot_room.common_context = "hello world";
        interviewee.setCursor(6);
        interviewer.setCursor(6);
        Changes old_change = new Changes();
        old_change.Init_index = 4;
        old_change.End_index = 6;
        old_change.change_type = "replace";
        old_change.context = "";
        old_change.size = 0;
        Changes new_change = ot_room.recruiter_send_update(old_change);
        assertTrue(ot_room.common_context.equals("hellworld"));
        assertTrue(new_change.cursor_move == -2);
    }

    @Test
    void recruiter_replace_middle_increase() {
        ot_room.common_context = "hello world";
        interviewee.setCursor(9);
        interviewer.setCursor(6);
        Changes old_change = new Changes();
        old_change.Init_index = 6;
        old_change.End_index = 11;
        old_change.change_type = "replace";
        old_change.context = "TestingBruh";
        old_change.size = 11;
        Changes new_change = ot_room.recruiter_send_update(old_change);
        assertTrue(ot_room.common_context.equals("hello TestingBruh"));
        assertTrue(new_change.cursor_move == 8);
    }

    @Test
    void recruiter_replace_right() {
        ot_room.common_context = "hello world";
        interviewee.setCursor(0);
        interviewer.setCursor(6);
        Changes old_change = new Changes();
        old_change.Init_index = 6;
        old_change.End_index = 11;
        old_change.change_type = "replace";
        old_change.context = "Nothing";
        old_change.size = 7;
        Changes new_change = ot_room.recruiter_send_update(old_change);
        System.out.println("Replace_right test: string is: " + ot_room.common_context);
        assertTrue(ot_room.common_context.equals("hello Nothing"));
        System.out.println("cursor_right move is: " + new_change.cursor_move);
        assertTrue(new_change.cursor_move == 0);
    }

    @Test
    void recruiter_replace_left_increase() {
        ot_room.common_context = "hello world";
        interviewee.setCursor(11);
        interviewer.setCursor(6);
        Changes old_change = new Changes();
        old_change.Init_index = 0;
        old_change.End_index = 6;
        old_change.change_type = "replace";
        old_change.context = "No Hello for ";
        old_change.size = 13;
        Changes new_change = ot_room.recruiter_send_update(old_change);
        System.out.println("Replace_left_increase test: string is: " + ot_room.common_context);
        assertTrue(ot_room.common_context.equals("No Hello for world"));
        System.out.println("cursor_left_increase_test move is: " + new_change.cursor_move);
        assertTrue(new_change.cursor_move == 2);
    }

}
