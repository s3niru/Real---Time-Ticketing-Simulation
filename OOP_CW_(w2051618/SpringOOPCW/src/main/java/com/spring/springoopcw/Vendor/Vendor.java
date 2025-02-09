package com.spring.springoopcw.Vendor;

import com.spring.springoopcw.Logs.Logger;
import com.spring.springoopcw.TicketPool.TicketPool;

/**
 * Represents a vendor that interacts with the ticket pool to add tickets.
 * This class implements Runnable to allow the vendor to run in a separate thread.
 */
public class Vendor implements Runnable {

    private TicketPool ticketPool; // Ticket pool to interact with
    private final int VendorID; // Unique ID for the vendor
    private boolean running; // Flag to control the vendor thread
    private int ticketsAdded; // Counter for tickets added by the vendor
    private int noOfTickets;
    private final VendorRepository vendorRepository; // Repository for persisting vendor data
    Logger logger = new Logger();

    /**
     * Constructor to initialize the Vendor.
     *
     * @param ticketPool         The ticket pool for adding tickets.
     * @param VendorID           Unique ID for the vendor.
     * @param vendorRepository   Repository for saving vendor data.
     * @param noOfTickets  Number of tickets added by the vendor.
     */
    public Vendor(TicketPool ticketPool, int VendorID, VendorRepository vendorRepository,int noOfTickets) {
        this.ticketPool = ticketPool;
        this.VendorID = VendorID;
        this.running = false;
        this.vendorRepository = vendorRepository;
        this.ticketsAdded = 0;
        this.noOfTickets = noOfTickets;
    }

    /**
     * Main logic for the vendor to add tickets continuously while running.
     */
    @Override
    public void run() {
        while (running) {
            if(noOfTickets > 0){
            int num = this.ticketPool.addTickets(VendorID); // Attempt to add a ticket
            noOfTickets --;
            ticketsAdded += num; // Increment tickets added if successful
            saveToDatabase(); // Persist the updated data to the database
        }else{
                System.out.println("\nVendor"+VendorID+" thread ended.");
                System.out.println("Vendor"+VendorID+" added tickets: "+ticketsAdded);
                logger.saveLog("Vendor"+VendorID+" thread ended.\nVendor"+VendorID+" added tickets:"+ticketsAdded);

            break;
            }
        }
    }

    /**
     * Starts the vendor thread.
     */
    public void start() {
        running = true;
        new Thread(this).start();
    }

    /**
     * Stops the vendor thread and saves the final data to the database.
     */
    public void stop() {
        running = false;
        saveToDatabase();
        System.out.println("\nVendor"+VendorID+ " thread stopped.");
        System.out.println("Vendor"+VendorID+" added tickets: "+ticketsAdded);
        logger.saveLog("Vendor"+VendorID+" thread stopped.\nVendor"+VendorID+" added tickets: "+ticketsAdded);

    }

    /**
     * Persists the vendor's data to the database.
     */
    private void saveToDatabase() {
        VendorEntity vendorEntity = new VendorEntity();
        vendorEntity.setVendorId(VendorID);
        vendorEntity.setTicketsAdded(ticketsAdded);
        vendorRepository.save(vendorEntity); // Save the entity to the database
    }
}