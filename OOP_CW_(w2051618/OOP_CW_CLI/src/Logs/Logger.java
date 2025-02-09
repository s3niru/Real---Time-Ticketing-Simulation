package Logs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Logger class responsible for logging different types of messages such as logs, errors, and warnings.
 * It writes the messages to respective files, appends new messages, and allows for file deletion.
 */
public class Logger {

    /**
     * Saves a log message to the "Logs.txt" file with a timestamp.
     *
     * @param message The message to log.
     */
    public void saveLog(String message) {
        try {
            // Create a FileWriter to append to the "Logs.txt" file
            FileWriter file = new FileWriter("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\OOP_CW_CLI\\src\\Logs\\Logs.txt", true);
            // Write the timestamp and message to the file
            file.write("\n");
            file.write("\n" + LocalDateTime.now() + ": \n" + message);
            // Ensure the message is written to the file
            file.flush();
        } catch (IOException e) {  // Catch any IOExceptions that occur
            // Print error message if there is an issue
            System.out.println("Error - " + e);
        }
    }

    /**
     * Saves an error message to the "Errors.txt" file with a timestamp.
     *
     * @param message The error message to log.
     */
    public void saveError(String message) {
        try {
            // Create a FileWriter to append to the "Errors.txt" file
            FileWriter file = new FileWriter("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\OOP_CW_CLI\\src\\Logs\\Errors.txt", true);
            // Write the timestamp and message to the file
            file.write("\n");
            file.write("\n" + LocalDateTime.now() + ": \n" + message);
            // Ensure the message is written to the file
            file.flush();
        } catch (IOException e) {  // Catch any IOExceptions that occur
            // Print error message if there is an issue
            System.out.println("Error - " + e);
        }
    }

    /**
     * Saves a warning message to the "Warnings.txt" file with a timestamp.
     *
     * @param message The warning message to log.
     */
    public void saveWarning(String message) {
        try {
            // Create a FileWriter to append to the "Warnings.txt" file
            FileWriter file = new FileWriter("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\OOP_CW_CLI\\src\\Logs\\Warnings.txt", true);
            // Write the timestamp and message to the file
            file.write("\n");
            file.write("\n" + LocalDateTime.now() + ": \n" + message);
            // Ensure the message is written to the file
            file.flush();
        } catch (IOException e) {  // Catch any IOExceptions that occur
            // Print error message if there is an issue
            System.out.println("Error - " + e);
        }
    }

    /**
     * Deletes all log, warning, and error log files if they exist.
     * This is used to clean up the log files when necessary.
     */
    public void deleteFiles() {
        // Create File objects representing the log files
        File logFile = new File("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\OOP_CW_CLI\\src\\Logs\\Logs.txt");
        File warningFile = new File("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\OOP_CW_CLI\\src\\Logs\\Errors.txt");
        File errorFile = new File("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\OOP_CW_CLI\\src\\Logs\\Warnings.txt");

        // Check if each file exists and delete it if it does
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
