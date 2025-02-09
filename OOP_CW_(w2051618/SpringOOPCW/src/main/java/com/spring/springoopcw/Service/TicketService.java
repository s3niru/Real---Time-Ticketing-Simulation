package com.spring.springoopcw.Service;

import com.google.gson.Gson;
import com.spring.springoopcw.Configuration.Configuration;
import com.spring.springoopcw.Customer.Customer;
import com.spring.springoopcw.Customer.CustomerRepository;
import com.spring.springoopcw.Logs.Logger;
import com.spring.springoopcw.TicketPool.TicketPool;
import com.spring.springoopcw.Vendor.Vendor;
import com.spring.springoopcw.Vendor.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Service class for managing the Ticket System.
 * This class handles the initialization, starting, and stopping of the system,
 * including creating vendors and customers based on the configuration.
 */
@Service
public class TicketService {

    // List to store ticket values
    List<Integer> tickets = new LinkedList<>();

    // List to store vendor objects
    List<Vendor> vendors = new LinkedList<>();

    // List to store customer objects
    List<Customer> customers = new LinkedList<>();

    @Autowired
    private VendorRepository vendorRepository; // Repository to handle vendor data

    @Autowired
    private CustomerRepository customerRepository; // Repository to handle customer data

    /**
     * Starts the ticket system by loading the configuration and initializing tickets, vendors, and customers.
     */
    public void startSystem() {
        // Load configuration from a JSON file
        Configuration config = loadFromJason("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\Configuration\\config.json");
        TicketPool ticketPool = new TicketPool(tickets, config);
        Random rand  = new Random();

        // Initialize tickets if the list is empty
        if (tickets.size() == 0) {
            for (int i = 0; i < config.getTotalTickets(); i++) {
                tickets.add(1);
            }
        }

        // Create and start vendors based on the configuration
        for (int i = 0; i < config.getNoOfVendors(); i++) {
            Vendor vendor = new Vendor(ticketPool, i + 1, vendorRepository, rand.nextInt(1,20));
            vendors.add(vendor);
            vendor.start();
        }

        // Create and start customers based on the configuration
        for (int i = 0; i < config.getNoOfCustomers(); i++) {
            Customer customer = new Customer(ticketPool, i + 1, customerRepository, rand.nextInt(1,20));
            customers.add(customer);
            customer.start();
        }
    }

    /**
     * Stops the ticket system by stopping all vendors and customers.
     */
    public void stopSystem() {
        // Stop all vendors
        for (Vendor vendor : vendors) {
            vendor.stop();
        }

        // Stop all customers
        for (Customer customer : customers) {
            customer.stop();
        }
    }

    /**
     * Loads the configuration from a JSON file.
     *
     * @param filepath The path to the configuration file.
     * @return Configuration object with the loaded settings.
     */
    public static Configuration loadFromJason(String filepath) {
        Logger logger = new Logger();
        Gson gson = new Gson();

        // Read the configuration file and parse it into a Configuration object
        try (FileReader reader = new FileReader(filepath)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace for debugging
            logger.saveError(String.valueOf(e)); // Log the error
            return null; // Return null in case of an error
        }
    }
}
