package com.example.hackertimebackend.OTStuff;

public class OT {

    public String common_context;
    public OTUser recruiter;
    public OTUser interviewee;

    public Changes recruiter_send_update(Changes original) {
        Changes interviewee_change = new Changes();

        interviewee_change.Init_index = original.Init_index;
        interviewee_change.change_type = original.change_type;
        interviewee_change.context = original.context;

        if (original.change_type.equals("add")) {
            // If the other client's cursor position is at the left of the change, don't update cursor
            if (original.Init_index > interviewee.getCursor()) {
                interviewee_change.cursor_move = 0;
            } else 
            // Else, calculate how many bit must the other client shift right
            {
                interviewee_change.cursor_move = original.context.length();
            }
            // Locally update cursor info
            recruiter.setCursor(recruiter.getCursor() + original.context.length());
            interviewee.setCursor(recruiter.getCursor() + interviewee_change.cursor_move);
            StringBuilder temp = new StringBuilder(common_context);
            temp.insert(original.Init_index, original.context);
            common_context = temp.toString();
            return interviewee_change;
        }
        // Invalid change request, return null
        return null;
    }
}