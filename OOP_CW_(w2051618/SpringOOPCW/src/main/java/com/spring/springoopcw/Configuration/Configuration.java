package com.spring.springoopcw.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spring.springoopcw.Logs.Logger;

import java.io.FileWriter;
import java.io.IOException;

/**
 * This class handles the configuration settings for the ticket system.
 * It stores various configuration parameters such as total tickets, ticket release rate,
 * customer retrieval rate, max ticket capacity, number of vendors, and number of customers.
 * The class provides getter and setter methods for these parameters and saves the configuration
 * data to a JSON file whenever a property is updated.
 */
public class Configuration {

    // Configuration parameters
    private int totalTickets;          // Total number of tickets available
    private int ticketReleaseRate;     // Rate at which tickets are released
    private int customerRetrievalRate; // Rate at which customers retrieve tickets
    private int maxTicketCapacity;     // Maximum capacity for tickets
    private int noOfVendors;           // Number of vendors available
    private int noOfCustomers;         // Number of customers requesting tickets

    //Getters and Setters
    public int getTotalTickets() {
        return totalTickets;
    }


    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
        saveToJason("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\Configuration\\config.json");
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }


    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
        saveToJason("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\Configuration\\config.json");
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }


    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
        saveToJason("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\Configuration\\config.json");
    }


    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }


    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
        saveToJason("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\Configuration\\config.json");
    }


    public int getNoOfVendors() {
        return noOfVendors;
    }


    public void setNoOfVendors(int noOfVendors) {
        this.noOfVendors = noOfVendors;
        saveToJason("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\Configuration\\config.json");
    }


    public int getNoOfCustomers() {
        return noOfCustomers;
    }

    public void setNoOfCustomers(int noOfCustomers) {
        this.noOfCustomers = noOfCustomers;
        saveToJason("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\Configuration\\config.json");
    }

    /**
     * Provides a string representation of the configuration object.
     * @return A string representing the configuration.
     */
    @Override
    public String toString() {
        return "Configuration{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                '}';
    }

    /**
     * Saves the current configuration to a JSON file at the specified file path.
     * The file is written using Gson for pretty printing.
     * @param filepath The file path where the configuration should be saved.
     */
    public void saveToJason(String filepath) {
        Logger logger = new Logger();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filepath)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
            // Log error in case of IOException
            logger.saveError(String.valueOf(e));
        }
    }
}


