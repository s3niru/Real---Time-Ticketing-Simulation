package Configuration;

import Logs.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Configuration class manages the settings related to ticket pool,
 * such as the number of tickets, release rates, customer retrieval rates, and the maximum ticket capacity.
 * It also provides methods to save configuration settings to a JSON file whenever they are updated.
 */
public class Configuration {

    // Variables storing configuration values
    private int totalTickets;  // The total number of tickets in the pool
    private int ticketReleaseRate;  // The rate at which tickets are added to the pool by vendors
    private int customerRetrievalRate;  // The rate at which customers can retrieve tickets
    private int maxTicketCapacity;  // The maximum capacity of tickets that can be stored in the pool
    private int noOfVendors;  // The number of vendors available
    private int noOfCustomers;  // The number of customers available

    // Getter and setter methods

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
     * Provides a string representation of the configuration values.
     *
     * @return A string describing the current configuration of the ticket pool.
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
     * Saves the current configuration to a JSON file.
     * This method is called whenever a configuration value is updated.
     *
     * @param filepath The path to the JSON file where the configuration will be saved.
     */
    public void saveToJason(String filepath) {
        Logger logger = new Logger();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filepath)) {
            // Convert the configuration object to JSON and write it to the file
            gson.toJson(this, writer);
        } catch (IOException e) {
            // Log any IOExceptions that occur while saving the configuration
            e.printStackTrace();
            logger.saveError(String.valueOf(e));
        }
    }

}
