package com.example.hackertimebackend.WebSocketData;

public class InterviewRoomSetting {
    public String RoomCode;
    public String InterviewerName;
    public String IntervieweeName;

    public InterviewRoomSetting() {
    }

    public InterviewRoomSetting(String RoomCode, String InterviewerName, String IntervieweeName) {
        this.RoomCode = RoomCode;
        this.InterviewerName = InterviewerName;
        this.IntervieweeName = IntervieweeName;
    }

    public String getRoomCode() {
        return this.RoomCode;
    }

    public void setRoomCode(String RoomCode) {
        this.RoomCode = RoomCode;
    }

    public String getInterviewerName() {
        return this.InterviewerName;
    }

    public void setInterviewerName(String InterviewerName) {
        this.InterviewerName = InterviewerName;
    }

    public String getIntervieweeName() {
        return this.IntervieweeName;
    }

    public void setIntervieweeName(String IntervieweeName) {
        this.IntervieweeName = IntervieweeName;
    }

    public InterviewRoomSetting RoomCode(String RoomCode) {
        setRoomCode(RoomCode);
        return this;
    }

    public InterviewRoomSetting InterviewerName(String InterviewerName) {
        setInterviewerName(InterviewerName);
        return this;
    }

    public InterviewRoomSetting IntervieweeName(String IntervieweeName) {
        setIntervieweeName(IntervieweeName);
        return this;
    }
}
