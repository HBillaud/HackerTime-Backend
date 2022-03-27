package com.example.hackertimebackend.WebSocket;

public class CodeStruct {
    String code;
    String lang;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return this.lang;
    }

    public void setLanguage(String lang) {
        this.lang = lang;
    }

    public CodeStruct(String code, String lang) {
        this.code = code;
        this.lang = lang;
    }

}
