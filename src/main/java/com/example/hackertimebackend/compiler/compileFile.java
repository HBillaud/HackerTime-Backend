package com.example.hackertimebackend.compiler;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Null;


public class compileFile {

    int Hitman_kill_count = 10;

    public String createTempFile(String content, String type) throws IOException {
        String name = generate_file_name(type);
        File file = new File(name);
        file.createNewFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        try {
            out.write(content);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "1";
        }
        Hitman hitman = new Hitman(Hitman_kill_count, name);
        System.out.println(hitman.getWorkingDir());
        hitman.start();
        return name;
    }

    public String generate_bash_script(String file_name) throws IOException {
        System.err.println("file_name_is:\n" + file_name);
        String[] no_suffix = file_name.split("\\.");
        String bash_name = no_suffix[0] + ".sh";
        File bash = new File(bash_name);
        bash.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(bash));
        writer.write("gcc -o " + no_suffix[0] + ".exe " + file_name + "\n./" + no_suffix[0] + ".exe");
        writer.close();
        Hitman hitman = new Hitman(Hitman_kill_count, bash_name);
        hitman.start();
        return bash_name;
    }

    public String[] runBash(String bash_name) throws IOException {
        String line;
        String error = "";
        String output = "";
        ProcessBuilder pb = new ProcessBuilder("/bin/bash",bash_name);
        long pid = -1;
        try {
            Process p = pb.start();
            pid = p.pid();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
            
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        while ((line = err.readLine()) != null) {
            error += line;
        }
        while ((line = in.readLine()) != null) {
            output += line;
        }
        in.close();
        File folder = new File(".");
        File[] folder_list = folder.listFiles();

        for (File file : folder_list) {
            if (file.isFile()) {
                String[] splitter = file.getName().split("\\.");
                if (splitter.length == 2 && splitter[1].equals("exe")) file.delete();
            }
        }

        String[] output_arr = {output, error};
        return output_arr;
    }

    public String generate_file_name(String type) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String name;
        if (type.equals("C") || type.equals("c")) {
            name = dateFormat.format(date) + ".c";
        } else {
            return "Not Supported!";
        }
        return name;
    }

}
