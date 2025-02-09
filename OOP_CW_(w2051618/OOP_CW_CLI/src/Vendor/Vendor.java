package Vendor;

import Core.TicketPool;
import Logs.Logger;

/**
 * Vendor class implements Runnable to simulate a vendor adding tickets to the ticket pool.
 * Each vendor runs in its own thread and adds tickets until a specified number is reached.
 */
public class Vendor implements Runnable {
    // TicketPool instance to interact with the ticket pool
    TicketPool ticketPool;
    // Logger instance to log the actions performed by the vendor
    Logger logger = new Logger();
    // Unique Vendor ID
    private final int VendorID;
    // Flag to control whether the vendor thread is running
    private boolean running;
    // Number of tickets the vendor is supposed to add
    private int noOfTickets;
    // Number of tickets added by the vendor
    private int noTicketsAdded;

    /**
     * Constructor for the Vendor class.
     *
     * @param ticketPool Instance of TicketPool to interact with the pool
     * @param VendorID The unique identifier for the vendor
     * @param noOfTickets Total tickets the vendor is allowed to add
     */
    public Vendor(TicketPool ticketPool, int VendorID, int noOfTickets) {
        this.ticketPool = ticketPool;
        this.VendorID = VendorID;
        this.running = false;
        this.noOfTickets = noOfTickets;
    }

    /**
     * run method is executed when the vendor thread starts.
     * The vendor adds tickets to the pool until the specified number of tickets is reached.
     */
    @Override
    public void run() {
        while (running) {
            // Check if the vendor has more tickets to add
            if (noOfTickets > noTicketsAdded) {
                // Add a single ticket to the pool and update the number of tickets added
                int num = this.ticketPool.addTickets(1, VendorID);
                noTicketsAdded += num;
            } else {
                // Once the vendor has added all their tickets, log and end the thread
                System.out.println("\nVendor" + VendorID + " Thread ended");
                System.out.println("Vendor" + VendorID + " added tickets: " + noTicketsAdded);
                logger.saveLog("Vendor" + VendorID + " Thread ended");
                break;
            }
        }
    }

    /**
     * start method to initiate the vendor thread.
     * This method starts the run method in a new thread.
     */
    public void start() {
        running = true;
        new Thread(this).start();
    }
}
