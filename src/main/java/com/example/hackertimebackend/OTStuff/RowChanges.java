package com.example.hackertimebackend.OTStuff;

import java.util.HashMap;

public class RowChanges {
    public HashMap<Integer, String> row_change;
    public String from_user;

    public RowChanges(HashMap<Integer, String> row_change, String from_user) {
        this.row_change = row_change;
        this.from_user = from_user;
    }

    public RowChanges() {
        this.row_change = new HashMap<Integer, String>();
        this.from_user = "mp";
    }

}