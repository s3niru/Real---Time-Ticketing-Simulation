package Customer;

import Core.TicketPool;
import Logs.Logger;

/**
 * Customer class that simulates a customer buying tickets from a ticket pool.
 * The customer runs on a separate thread to remove tickets from the pool until they have bought the desired number of tickets.
 */
public class Customer implements Runnable {

    // The ticket pool from which tickets will be bought
    TicketPool ticketPool;
    // Logger to record actions related to the customer
    Logger logger = new Logger();
    // The unique ID of the customer
    private final int BuyerID;
    // Flag to indicate if the customer thread is running
    private boolean running;
    // The total number of tickets the customer wants to buy
    private int noOfTickets;
    // The number of tickets the customer has bought so far
    private int noticketsBought;

    /**
     * Constructor to initialize a new Customer.
     *
     * @param ticketPool The ticket pool from which tickets will be bought.
     * @param buyerID The unique ID of the customer.
     * @param noOfTickets The number of tickets the customer wants to buy.
     */
    public Customer(TicketPool ticketPool, int buyerID, int noOfTickets) {
        this.ticketPool = ticketPool;
        this.BuyerID = buyerID;
        this.running = false;
        this.noOfTickets = noOfTickets;
    }

    /**
     * The run method that executes the ticket-buying process in a separate thread.
     * The customer continues to buy tickets until they have bought the desired number.
     */
    @Override
    public void run() {
        // Loop runs while the 'running' flag is true
        while (running) {
            // Check if the customer has not yet bought the required number of tickets
            if (noOfTickets > noticketsBought) {
                // Remove 1 ticket from the pool for the customer
                int num = this.ticketPool.removeTickets(BuyerID);
                // Update the number of tickets the customer has bought
                noticketsBought += num;
            } else {
                // When the customer has bought the desired number of tickets, end the thread
                System.out.println("\nBuyer" + BuyerID + " Thread ended");
                System.out.println("Buyer" + BuyerID + " total tickets bought: " + noticketsBought);
                // Log that the buyer's thread has ended
                logger.saveLog("Buyer" + BuyerID + "Thread ended");
                // Break out of the loop to stop the thread
                break;
            }
        }
    }

    /**
     * Starts the customer thread, allowing the customer to begin buying tickets.
     */
    public void start() {
        // Set the running flag to true to start the thread
        running = true;
        // Start a new thread to execute the run method
        new Thread(this).start();
    }
}
