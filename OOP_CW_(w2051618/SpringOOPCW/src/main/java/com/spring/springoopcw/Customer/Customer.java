package com.spring.springoopcw.Customer;


import com.spring.springoopcw.Logs.Logger;
import com.spring.springoopcw.TicketPool.TicketPool;

/**
 * Represents a customer that interacts with the ticket pool to buy tickets.
 * This class implements Runnable to allow the customer to run in a separate thread.
 */
public class Customer implements Runnable {

    private TicketPool ticketPool; // Ticket pool to interact with
    private final int BuyerID; // Unique ID for the customer
    private boolean running; // Flag to control the customer thread
    private int ticketsBought; // Counter for tickets bought by the customer
    private int noOfTickets;
    private final CustomerRepository customerRepository;// Repository for persisting customer data
    Logger logger = new Logger();

    /**
     * Constructor to initialize the Customer.
     *
     * @param ticketPool        The ticket pool for buying tickets.
     * @param buyerID           Unique ID for the customer.
     * @param customerRepository Repository for saving customer data.
     * @param noOfTickets Number of tickets which will be bought by the customer.
     */
    public Customer(TicketPool ticketPool, int buyerID, CustomerRepository customerRepository, int noOfTickets) {
        this.ticketPool = ticketPool;
        this.BuyerID = buyerID;
        this.running = false;
        this.ticketsBought = 0;
        this.customerRepository = customerRepository;
        this.noOfTickets = noOfTickets;
    }

    /**
     * Main logic for the customer to buy tickets continuously while running.
     */
    @Override
    public void run() {
        while (running) {
            if(noOfTickets>0){
            int num = this.ticketPool.removeTickets(BuyerID); // Attempt to buy a ticket
            ticketsBought += num; // Increment tickets bought if successful
                noOfTickets--;
            saveToDatabase(); // Persist the updated data to the database
        }else{
            System.out.println("\nBuyer" + BuyerID + " thread ended.");
            System.out.println("Buyer" + BuyerID + " bought tickets: " + ticketsBought);
            logger.saveLog("Buyer" + BuyerID + " thread ended.\nCustomer" + BuyerID + " bought tickets: " + ticketsBought);
            break;
        }}
    }

    /**
     * Starts the customer thread.
     */
    public void start() {
        running = true;
        new Thread(this).start();
    }

    /**
     * Stops the customer thread and saves the final data to the database.
     */
    public void stop() {
        running = false;
        System.out.println("\nBuyer" + BuyerID + " thread stopped.");
        System.out.println("Buyer" + BuyerID + " bought tickets: " + ticketsBought);
        logger.saveLog("Buyer" + BuyerID + " thread stopped.\nCustomer" + BuyerID + " bought tickets: " + ticketsBought);
        saveToDatabase();
    }

    /**
     * Persists the customer's data to the database.
     */
    private void saveToDatabase() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerId(BuyerID);
        customerEntity.setTicketsBought(ticketsBought);
        customerRepository.save(customerEntity); // Save the entity to the database
    }
}
