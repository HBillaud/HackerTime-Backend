package com.example.hackertimebackend.compiler;

import com.example.hackertimebackend.WebSocket.CodeStruct;

import java.io.IOException;
import java.util.Map;

public interface compileService {
    Map<String, String> compile(CodeStruct code) throws IOException;
}
