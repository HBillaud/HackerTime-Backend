package com.example.hackertimebackend.OTStuff;

import java.util.ArrayList;
import java.util.HashMap;

public class OT {

    public HashMap<Integer, String> common_context;
    public String actual_code;
    public OTUser recruiter;
    public OTUser interviewee;
    public ArrayList<String> recruiter_queue;
    public ArrayList<String> interviewee_queue;

    public void initialize() {
        recruiter.setCursor(0);
        interviewee.setCursor(0);
        // Used to store change queries to handle synchronization issues. Unused for now
        recruiter_queue = new ArrayList<String>();
        interviewee_queue = new ArrayList<String>();
        common_context = new HashMap<Integer, String>();
    }

    public RowChanges recruiter_send_update(RowChanges original_row) {

        RowChanges interviewee_change = new RowChanges();
        interviewee_change.from_user = "recruiter";
        interviewee_change.row_change = new HashMap<>(original_row.row_change);
        for (Integer rows : original_row.row_change.keySet()) {
            String original = original_row.row_change.get(rows);
            common_context.put(rows, original);
        }
        return original_row;

        // Invalid change request, return null
    }

    public RowChanges interviewee_send_update(RowChanges original_row) {
        RowChanges interviewee_change = new RowChanges();
        interviewee_change.from_user = "recruiter";
        interviewee_change.row_change = new HashMap<>(original_row.row_change);
        for (Integer rows : original_row.row_change.keySet()) {
            String original = original_row.row_change.get(rows);
            common_context.put(rows, original);
        }
        return original_row;

        // Invalid change request, return null
    }
}