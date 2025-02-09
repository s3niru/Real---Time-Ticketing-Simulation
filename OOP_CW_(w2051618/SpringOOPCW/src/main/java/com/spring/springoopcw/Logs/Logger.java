package com.spring.springoopcw.Logs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Logger class provides methods for logging messages, errors, and warnings into
 * different log files. The class also supports deleting the log files when necessary.
 */
public class Logger {

    /**
     * Saves a regular log message into the "Logs.txt" file with the current timestamp.
     * Each log entry is written in the format of: [current date and time]: [message].
     *
     * @param message The log message to be saved.
     */
    public void saveLog(String message) {
        try {
            // Create a FileWriter instance to append to the "Logs.txt" file
            FileWriter file = new FileWriter("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\SpringOOPCW\\src\\main\\java\\com\\spring\\springoopcw\\Logs\\Logs.txt", true);
            // Write the current timestamp and the provided message to the file
            file.write("\n");
            file.write("\n" + LocalDateTime.now() + ": \n" + message);
            file.flush();  // Ensure the data is written to the file
        } catch (IOException e) { // Catch any errors during file writing
            System.out.println("Error - " + e); // Display the error message
        }
    }

    /**
     * Saves an error message into the "Errors.txt" file with the current timestamp.
     * Each error entry is written in the format of: [current date and time]: [error message].
     *
     * @param message The error message to be saved.
     */
    public void saveError(String message) {
        try {
            // Create a FileWriter instance to append to the "Errors.txt" file
            FileWriter file = new FileWriter("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\SpringOOPCW\\src\\main\\java\\com\\spring\\springoopcw\\Logs\\Errors.txt", true);
            // Write the current timestamp and the provided error message to the file
            file.write("\n");
            file.write("\n" + LocalDateTime.now() + ": \n" + message);
            file.flush();  // Ensure the data is written to the file
        } catch (IOException e) { // Catch any errors during file writing
            System.out.println("Error - " + e); // Display the error message
        }
    }

    /**
     * Saves a warning message into the "Warnings.txt" file with the current timestamp.
     * Each warning entry is written in the format of: [current date and time]: [warning message].
     *
     * @param message The warning message to be saved.
     */
    public void saveWarning(String message) {
        try {
            // Create a FileWriter instance to append to the "Warnings.txt" file
            FileWriter file = new FileWriter("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\SpringOOPCW\\src\\main\\java\\com\\spring\\springoopcw\\Logs\\Warnings.txt", true);
            // Write the current timestamp and the provided warning message to the file
            file.write("\n");
            file.write("\n" + LocalDateTime.now() + ": \n" + message);
            file.flush();  // Ensure the data is written to the file
        } catch (IOException e) { // Catch any errors during file writing
            System.out.println("Error - " + e); // Display the error message
        }
    }

    /**
     * Deletes the log files ("Logs.txt", "Errors.txt", and "Warnings.txt") if they exist.
     * This method is useful for clearing the log data when necessary.
     */
    public void deleteFiles() {
        // Define the log files that may need to be deleted
        File logFile = new File("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\SpringOOPCW\\src\\main\\java\\com\\spring\\springoopcw\\Logs\\Logs.txt");
        File warningFile = new File("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\SpringOOPCW\\src\\main\\java\\com\\spring\\springoopcw\\Logs\\Errors.txt");
        File errorFile = new File("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\SpringOOPCW\\src\\main\\java\\com\\spring\\springoopcw\\Logs\\Warnings.txt");

        // Check if the log files exist and delete them
        if (logFile.exists()) {
            logFile.delete();
        }
        if (warningFile.exists()) {
            warningFile.delete();
        }
        if (errorFile.exists()) {
            errorFile.delete();
        }
    }

}
