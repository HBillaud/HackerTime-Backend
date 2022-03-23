package com.example.hackertimebackend.compiler;

import java.io.File;

public class Hitman extends Thread{
    // Countdown timer to kill the file
    int Deadline;
    // Target file to delete
    String filename;

    public Hitman(int Deadline, String filename) {
        this.Deadline = Deadline;
        this.filename = filename;
    }

    public String getWorkingDir() {
        String direc = System.getProperty("user.dir");
        return direc;
    }


    public void run() {
        try {
            Thread.sleep(1000 * Deadline);
        } catch(InterruptedException ex) {
            // Need to carry this interrupt forward
            Thread.currentThread().interrupt();
        }

        File target = new File(filename);
        if (target.delete()) {
            System.out.println("file " + filename + " deleted.");
        } else {
            System.out.println("failed to delete " + filename + ".");
        }
        
    }

}
