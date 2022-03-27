package com.example.hackertimebackend.compiler;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class CompilerTest {
    @Test
    public void test1() throws IOException, InterruptedException {
        System.out.println("\nTesting java file creation...\n");
        compileFile compiler = new compileFile();
        String file_name = compiler.createTempFile("#import <stdio.h>\n\nint main() {\n    printf(\"Hello World!\");\n}", "C");
        assert(!file_name.equals("1"));
        File file = new File(file_name);
        assert(file.exists());
        Thread.sleep(10000);
        assert(!file.exists());
    }

    @Test
    public void test_bash_create() throws IOException, InterruptedException {
        System.out.println("\nTesting bash file creation...\n");
        compileFile compiler = new compileFile();
        String file_name = compiler.createTempFile("#import <stdio.h>\n\nint main() {\n    printf(\"Hello World!\");\n}", "C");
        assert(!file_name.equals("1"));
        File file = new File(file_name);
        assert(file.exists());
        String bash_file = compiler.generate_bash_script(file_name);
        File bash = new File(bash_file);
        assert(bash.exists());
    }

    @Test
    public void test_bash_run_good() throws IOException, InterruptedException {
        System.out.println("\nTesting bash file running...\n");
        compileFile compiler = new compileFile();
        String file_name = compiler.createTempFile("#import <stdio.h>\n\nint main() {\n    printf(\"Hello World!\");\n}", "C");
        assert(!file_name.equals("1"));
        File file = new File(file_name);
        assert(file.exists());
        String bash_file = compiler.generate_bash_script(file_name);
        File bash = new File(bash_file);
        assert(bash.exists());
        String[] output_arr = compiler.runBash(bash_file);
        System.out.println("stdout:\n" + output_arr[0]);
        System.out.println("stderr:\n" + output_arr[1]);
        assert(output_arr[0].equals("Hello World!"));
        Thread.sleep(10000);
        assert(!file.exists());
    }

    @Test
    public void test_bash_run_bad() throws IOException, InterruptedException {
        System.out.println("\nTesting bash file running...\n");
        compileFile compiler = new compileFile();
        String file_name = compiler.createTempFile("#import <stdio.h>\n\nint main() {\n    printf(\"Hello World!\")\n}", "C");
        assert(!file_name.equals("1"));
        File file = new File(file_name);
        assert(file.exists());
        String bash_file = compiler.generate_bash_script(file_name);
        File bash = new File(bash_file);
        assert(bash.exists());
        String[] output_arr = compiler.runBash(bash_file);
        System.out.println("stdout:\n" + output_arr[0]);
        System.out.println("stderr:\n" + output_arr[1]);
        assert(!output_arr[0].equals("Hello World!"));
        Thread.sleep(10000);
        assert(!file.exists());
    }
}
