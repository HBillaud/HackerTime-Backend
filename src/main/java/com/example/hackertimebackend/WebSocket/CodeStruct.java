package com.example.hackertimebackend.WebSocket;

public class CodeStruct {
    String code;
    String lang;

    public CodeStruct(String code, String lang) {
        this.code = code;
        this.lang = lang;
    }

    public CodeStruct() {}

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLang() {
        return this.lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

}
