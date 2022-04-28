package com.example.hackertimebackend.OTStuff;

import java.util.ArrayList;
import java.util.HashMap;

public class OT {

    public HashMap<Integer, String> common_context;
    public OTUser recruiter;
    public OTUser interviewee;
    public ArrayList<RowChanges> recruiter_queue;
    public ArrayList<RowChanges> interviewee_queue;

    public void initialize() {
        recruiter.setCursor(0);
        interviewee.setCursor(0);
        // Used to store change queries to handle synchronization issues. Unused for now
        recruiter_queue = new ArrayList<Changes>();
        interviewee_queue = new ArrayList<Changes>();
        common_context = new HashMap<Integer, String>();
    }

    public RowChanges recruiter_send_update(RowChanges original_row) {

        RowChanges interviewee_change = new RowChanges();
        interviewee_change.from_user = "recruiter";
        interviewee_change.row_change = new HashMap<>(original_row.row_change);
        for (Integer rows : original_row.row_change.keySet()) {
            Changes original = original_row.row_change.get(rows);
            if (original.change_type.equals("cursor_update")) {
                recruiter.setCursor(original.Init_index);
                return interviewee_change;
            }
            if (original.change_type.equals("add")) {
                // Locally update cursor info
                StringBuilder temp = new StringBuilder(common_context);
                temp.insert(original.Init_index, original.context);
                common_context = temp.toString();
                return interviewee_change;
            } 
            if (original.change_type.equals("delete")) {
                // If the other client's cursor position is at the right of the change, don't update cursor
                if (original.End_index > interviewee.getCursor()) {
                    interviewee_change.cursor_move = 0;
                } else 
                // Else, calculate how many bit must the other client shift right
                {
                    interviewee_change.cursor_move = -original.size;
                }
                // Locally update cursor info
                recruiter.setCursor(recruiter.getCursor() - original.size);
                interviewee.setCursor(recruiter.getCursor() + interviewee_change.cursor_move);
                StringBuilder temp = new StringBuilder(common_context);
                temp.delete(original.Init_index, original.End_index);
                System.out.println("\nFinal string is: " + temp.toString());
                common_context = temp.toString();
                return interviewee_change;
            }
            if (original.change_type.equals("replace")) {
                int diff = original.End_index - original.Init_index - original.context.length();
                if (original.End_index >= interviewee.getCursor()
                    && original.Init_index <= interviewee.getCursor()) {
                        System.out.println("I'm in here 1");
                        interviewee_change.cursor_move = original.Init_index - interviewee.getCursor() + original.context.length();
                }
                else if (original.End_index > interviewee.getCursor()
                    && original.Init_index > interviewee.getCursor()) {
                        System.out.println("I'm in here 2");
                        interviewee_change.cursor_move = 0;
                }
                else if (original.End_index < interviewee.getCursor()
                    && original.Init_index < interviewee.getCursor()) {
                        System.out.println("I'm in here 3");
                        interviewee_change.cursor_move = original.Init_index - interviewee.getCursor() + original.context.length();
                } else {
                    // The initial and ending index must be wrong, abort
                    return null;
                }
    
                recruiter.setCursor(recruiter.getCursor() + diff);
                interviewee.setCursor(interviewee.getCursor() + interviewee_change.cursor_move);
                StringBuilder temp = new StringBuilder(common_context);
                temp.replace(original.Init_index, original.End_index, original.context);
                System.out.println("\nFinal string is: " + temp.toString());
                common_context = temp.toString();
                return interviewee_change;
            }
        }
        interviewee_change.Init_index = original.Init_index;
        interviewee_change.change_type = original.change_type;
        
        
        // Invalid change request, return null
        return null;
    }

    public Changes interviewee_send_update(Changes original) {
        Changes recruiter_change = new Changes();
        recruiter_change.from_user = "interviewee";

        recruiter_change.Init_index = original.Init_index;
        recruiter_change.change_type = original.change_type;
        
        if (original.change_type.equals("cursor_update")) {
            recruiter.setCursor(original.Init_index);
            return recruiter_change;
        }
        if (original.change_type.equals("add")) {
            recruiter_change.context = original.context;
            // If the other client's cursor position is at the left of the change, don't update cursor
            if (original.Init_index > interviewee.getCursor()) {
                recruiter_change.cursor_move = 0;
            } else 
            // Else, calculate how many bit must the other client shift right
            {
                recruiter_change.cursor_move = original.context.length();
            }
            // Locally update cursor info
            interviewee.setCursor(interviewee.getCursor() + original.context.length());
            recruiter.setCursor(recruiter.getCursor() + recruiter_change.cursor_move);
            StringBuilder temp = new StringBuilder(common_context);
            temp.insert(original.Init_index, original.context);
            common_context = temp.toString();
            return recruiter_change;
        } 
        if (original.change_type.equals("delete")) {
            // If the other client's cursor position is at the right of the change, don't update cursor
            if (original.End_index > interviewee.getCursor()) {
                recruiter_change.cursor_move = 0;
            } else 
            // Else, calculate how many bit must the other client shift right
            {
                recruiter_change.cursor_move = -original.size;
            }
            // Locally update cursor info
            interviewee.setCursor(interviewee.getCursor() - original.size);
            recruiter.setCursor(recruiter.getCursor() + recruiter_change.cursor_move);
            StringBuilder temp = new StringBuilder(common_context);
            temp.delete(original.Init_index, original.End_index);
            System.out.println("\nFinal string is: " + temp.toString());
            common_context = temp.toString();
            return recruiter_change;
        }
        if (original.change_type.equals("replace")) {
            int diff = original.End_index - original.Init_index - original.context.length();
            if (original.End_index >= interviewee.getCursor()
                && original.Init_index <= interviewee.getCursor()) {
                    System.out.println("I'm in here 1");
                    recruiter_change.cursor_move = original.Init_index - interviewee.getCursor() + original.context.length();
            }
            else if (original.End_index > interviewee.getCursor()
                && original.Init_index > interviewee.getCursor()) {
                    System.out.println("I'm in here 2");
                    recruiter_change.cursor_move = 0;
            }
            else if (original.End_index < interviewee.getCursor()
                && original.Init_index < interviewee.getCursor()) {
                    System.out.println("I'm in here 3");
                    recruiter_change.cursor_move = original.Init_index - interviewee.getCursor() + original.context.length();
            } else {
                // The initial and ending index must be wrong, abort
                return null;
            }

            interviewee.setCursor(interviewee.getCursor() + diff);
            recruiter.setCursor(recruiter.getCursor() + recruiter_change.cursor_move);
            StringBuilder temp = new StringBuilder(common_context);
            temp.replace(original.Init_index, original.End_index, original.context);
            System.out.println("\nFinal string is: " + temp.toString());
            common_context = temp.toString();
            return recruiter_change;
        }
        // Invalid change request, return null
        return null;
    }
}