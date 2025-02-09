package Core;

import Logs.Logger;
import Configuration.Configuration;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * TicketPool class manages the pool of tickets available for sale or purchase.
 * It allows vendors to add tickets to the pool and customers to buy tickets from the pool,
 * while respecting rate limits on adding/removing tickets and ensuring synchronization between threads.
 */
public class TicketPool {

    // Configuration object loaded from a JSON file
    Configuration config;
    // List holding the available tickets
    private List<Integer> tickets;
    // Variable tracking the number of tickets added by vendors
    private int sellTicketNo = 1;
    // Variable tracking the number of tickets bought by customers
    private int buyTicketNo = 0;
    // Time tracking the total time taken by the vendor to add tickets
    private long sellTicketTime = 0;
    // Time tracking the total time taken by the buyer to buy tickets
    private long buyTicketTime = 0;
    // Logger for saving logs and error messages
    Logger logger = new Logger();

    /**
     * Constructor for TicketPool.
     * Initializes the ticket pool with a list of tickets and loads configuration from a JSON file.
     *
     * @param tickets A list of tickets to initialize the pool.
     */
    public TicketPool(List<Integer> tickets) {
        this.tickets = tickets;
        this.config = loadFromJason("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\Configuration\\config.json");
    }

    /**
     * Adds tickets to the pool, controlled by rate limits defined in the configuration.
     * Vendors can add tickets, but they must not exceed the ticket release rate.
     * If the pool is full, vendors cannot add more tickets.
     *
     * @param ticket The ticket to be added to the pool.
     * @param VendorID The ID of the vendor adding the ticket.
     * @return The number of tickets successfully added (1 if successful, 0 if failed).
     */
    public synchronized int addTickets(int ticket, int VendorID) {
        int num = 1;
        Random rand = new Random();
        int ranTime = rand.nextInt(2000);
        long starTime = System.nanoTime();

        // Check if the ticket pool has space for more tickets
        if (tickets.size() < config.getMaxTicketCapacity()) {
            // Ensure the vendor does not exceed the ticket release rate
            if (sellTicketNo <= config.getTicketReleaseRate()) {
                try {
                    Thread.sleep(ranTime);  // Simulate some processing delay
                } catch (InterruptedException e) {
                    logger.saveError(String.valueOf(e));
                    throw new RuntimeException(e);
                }

                // Add the ticket to the pool and update the ticket count
                this.tickets.add(ticket);
                config.setTotalTickets(tickets.size());

                // Log the action
                System.out.println("\nVendor" + VendorID + "\nadded ticket....\nAvailable tickets : " + config.getTotalTickets());
                logger.saveLog("Vendor" + VendorID + "\nadded ticket....\nAvailable tickets : " + config.getTotalTickets());

                sellTicketNo++;
                sellTicketTime += (System.nanoTime() - starTime);  // Track the time taken to add tickets

            } else if (sellTicketNo > config.getTicketReleaseRate()) {
                try {
                    // Log the warning and wait if the release rate is exceeded
                    System.out.println("\nVendor" + VendorID + "\nRelease rate exceeded....\nWaiting - " + ((15000 - (buyTicketTime / 1000000)) / 1000) + "S");
                    logger.saveWarning("\nVendor" + VendorID + "\nRelease rate exceeded....\nWaiting - " + ((15000 - (buyTicketTime / 1000000)) / 1000) + "S");
                    wait(15000 - (sellTicketTime / 1000000));  // Wait for the specified time
                    this.tickets.add(ticket);  // Add the ticket to the pool after waiting
                    config.setTotalTickets(tickets.size());

                    // Log the ticket addition
                    System.out.println("\nVendor" + VendorID + "\nadded ticket....\nAvailable tickets : " + config.getTotalTickets());
                    logger.saveLog("Vendor" + VendorID + "\nadded ticket....\nAvailable tickets : " + config.getTotalTickets());

                    sellTicketNo = 1;
                    sellTicketTime = 0;
                    notifyAll();  // Notify other threads

                } catch (InterruptedException e) {
                    logger.saveError(String.valueOf(e));
                    throw new RuntimeException(e);
                }
            }
        } else if (tickets.size() >= config.getMaxTicketCapacity()) {
            // If the ticket pool is full, log the warning and do not add more tickets
            System.out.println("\nVendor" + VendorID + "\nTicket pool is full......\nAvailable tickets : " + config.getTotalTickets());
            logger.saveWarning("Vendor" + VendorID + "\nTicket pool is full......\nAvailable tickets : " + config.getTotalTickets());
            num = 0;  // Indicate failure to add tickets
        }
        return num;  // Return the result (1 for success, 0 for failure)
    }

    /**
     * Removes a ticket from the pool for a customer to buy.
     * Customers can only buy tickets if they do not exceed the customer retrieval rate.
     * If the pool is empty, customers cannot buy tickets.
     *
     * @param BuyerID The ID of the customer trying to buy the ticket.
     * @return The number of tickets successfully bought (1 if successful, 0 if failed).
     */
    public synchronized int removeTickets(int BuyerID) {
        int num = 1;
        Random rand = new Random();
        int ranTime = rand.nextInt(2000);
        long starTime = System.nanoTime();

        try {
            // Check if the customer can buy the ticket without exceeding the retrieval rate
            if (buyTicketNo < config.getCustomerRetrievalRate()) {
                try {
                    Thread.sleep(ranTime);  // Simulate some processing delay
                } catch (InterruptedException e) {
                    logger.saveError(String.valueOf(e));
                    throw new RuntimeException(e);
                }

                // Remove the ticket from the pool and update the ticket count
                this.tickets.remove(0);
                config.setTotalTickets(tickets.size());

                // Log the action
                System.out.println("\nBuyer" + BuyerID + "\nbought ticket....\nAvailable tickets : " + config.getTotalTickets());
                logger.saveLog("Buyer" + BuyerID + "\nbought ticket....\nAvailable tickets : " + config.getTotalTickets());

                buyTicketNo++;
                buyTicketTime += (System.nanoTime() - starTime);  // Track the time taken to buy tickets

            } else if (buyTicketNo >= config.getCustomerRetrievalRate()) {
                try {
                    // Log the warning and wait if the retrieval rate is exceeded
                    System.out.println("\nBuyer" + BuyerID + "\nRetrieval rate exceeded....\nWaiting - " + ((15000 - (buyTicketTime / 1000000)) / 1000) + "S");
                    logger.saveWarning("Buyer" + BuyerID + "\nRetrieval rate exceeded....\nWaiting - " + ((15000 - (buyTicketTime / 1000000)) / 1000) + "S");
                    wait(15000 - (buyTicketTime / 1000000));  // Wait for the specified time
                    this.tickets.remove(0);  // Remove the ticket after waiting
                    config.setTotalTickets(tickets.size());

                    // Log the action
                    System.out.println("\nBuyer" + BuyerID + "\nTicket bought...\nAvailable tickets : " + config.getTotalTickets());
                    logger.saveLog("Buyer" + BuyerID + "\nbought ticket....\nAvailable tickets : " + config.getTotalTickets());

                    buyTicketNo = 1;
                    buyTicketTime = 0;
                    notifyAll();  // Notify other threads

                } catch (InterruptedException e) {
                    logger.saveError(String.valueOf(e));
                    throw new RuntimeException(e);
                }

            }
        } catch (IndexOutOfBoundsException e) {
            // If there are no tickets left to buy, log the warning
            System.out.println("\nBuyer" + BuyerID + "\nNo ticket available....\nAvailable tickets : " + config.getTotalTickets());
            logger.saveWarning("Buyer" + BuyerID + "\nNo ticket available....\nAvailable tickets : " + config.getTotalTickets());
            num = 0;  // Indicate failure to buy tickets
        }
        return num;  // Return the result (1 for success, 0 for failure)
    }

    /**
     * Loads configuration data from a JSON file.
     *
     * @param filepath The path to the configuration file.
     * @return A Configuration object containing the data loaded from the file.
     */
    public Configuration loadFromJason(String filepath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filepath)) {
            // Parse the JSON file and return the Configuration object
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
            logger.saveError(String.valueOf(e));
            return null;  // Return null if there is an error reading the file
        }
    }
}
