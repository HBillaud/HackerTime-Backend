package com.example.hackertimebackend.compiler;

import com.example.hackertimebackend.WebSocket.CodeStruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class compileServiceImpl implements compileService {

    @Override
    public Map<String, String> compile(CodeStruct code) throws IOException {
        System.out.println(code.getLang());
        System.out.println(code.getCode());
        compileFile compiler = new compileFile();
        String name = compiler.createTempFile(code.getCode(), code.getLang());
        String bash_name = compiler.generate_bash_script(name);
        String[] result = compiler.runBash(bash_name);
        Map<String, String> return_val = new HashMap<>();
        return_val.put("stdout", result[0]);
        return_val.put("stderr", result[1]);
        return return_val;
    }
}
