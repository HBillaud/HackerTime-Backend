package com.example.hackertimebackend.OTStuff;

import java.util.HashMap;

public class Changes {
    public int cursor_move;
    public String change_type;
    public int Init_index;
    public int End_index;
    public String context;
    public int size;


    public Changes(int cursor_move, String change_type, int Init_index, int End_index, String context, int size) {
        this.cursor_move = cursor_move;
        this.change_type = change_type;
        this.Init_index = Init_index;
        this.End_index = End_index;
        this.context = context;
        this.size = size;
    }

    public Changes() {
    }


    public Changes(int cursor_move, String change_type, int Init_index, int End_index, int size) {
        this.cursor_move = cursor_move;
        this.change_type = change_type;
        this.Init_index = Init_index;
        this.End_index = End_index;
        this.size = size;
    }


}
