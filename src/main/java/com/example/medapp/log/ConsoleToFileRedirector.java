package com.example.medapp.log;

/* import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.io.FileOutputStream;
import java.io.PrintStream;

@Component
public class ConsoleToFileRedirector {

    @PostConstruct
    public void init() {
        try {
            // Append to log file to preserve previous logs
            PrintStream fileOut = new PrintStream(new FileOutputStream("src/main/java/com/example/medapp/logFiles/application.txt", true), true);
            System.setOut(fileOut);
            System.setErr(fileOut);
            System.out.println("=== Logging to file started ===");
        } catch (Exception e) {
            e.printStackTrace(); // Only prints to the original console
        }
    }
}
 */