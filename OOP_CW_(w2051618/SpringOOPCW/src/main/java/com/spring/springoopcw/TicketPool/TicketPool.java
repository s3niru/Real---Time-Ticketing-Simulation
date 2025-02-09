package com.spring.springoopcw.TicketPool;

import com.spring.springoopcw.Configuration.Configuration;
import com.spring.springoopcw.Logs.Logger;

import java.util.List;
import java.util.Random;

/**
 * Represents a pool of tickets that can be managed by vendors and accessed by buyers.
 * Handles ticket addition and removal while adhering to rate limits and ticket capacity.
 */
public class TicketPool {

    private Configuration config; // Configuration settings for the ticket pool
    private List<Integer> tickets; // List representing the pool of tickets
    private int sellTicketNo = 1; // Counter for tickets sold by vendors
    private int buyTicketNo = 0; // Counter for tickets bought by customers
    private long sellTicketTime = 0; // Total time spent on selling tickets
    private long buyTicketTime = 0; // Total time spent on buying tickets
    Logger logger = new Logger(); // Logger to track events and errors

    /**
     * Constructor to initialize the TicketPool.
     *
     * @param tickets List to hold tickets.
     * @param config  Configuration object for system settings.
     */
    public TicketPool(List<Integer> tickets, Configuration config) {
        this.tickets = tickets;
        this.config = config;
    }

    /**
     * Adds tickets to the pool, adhering to the ticket release rate and maximum capacity.
     *
     * @param VendorID ID of the vendor adding the ticket.
     * @return 1 if the ticket was added successfully, 0 otherwise.
     */
    public synchronized int addTickets( int VendorID) {
        int num = 1;
        Random rand = new Random();
        int ranTime = rand.nextInt(2000); // Random delay time
        long starTime = System.nanoTime();

        if (tickets.size() < config.getMaxTicketCapacity()) {
            if (sellTicketNo <= config.getTicketReleaseRate()) {
                try {
                    Thread.sleep(ranTime); // Simulate processing delay
                } catch (InterruptedException e) {
                    logger.saveError(String.valueOf(e));
                    throw new RuntimeException(e);
                }
                this.tickets.add(1);
                config.setTotalTickets(tickets.size());
                System.out.println("\nVendor" + VendorID + "\nadded ticket....\nAvailable tickets : " + config.getTotalTickets());
                logger.saveLog("Vendor" + VendorID + "\nadded ticket....\nAvailable tickets : " + config.getTotalTickets());
                sellTicketNo++;
                sellTicketTime += (System.nanoTime() - starTime);
            } else if (sellTicketNo > config.getTicketReleaseRate()) {
                try {
                    System.out.println("\nVendor" + VendorID + "\nRelease rate exceeded....\nWaiting - " + ((15000 - (buyTicketTime / 1000000)) / 1000) + "S");
                    logger.saveWarning("\nVendor" + VendorID + "\nRelease rate exceeded....\nWaiting - " + ((15000 - (buyTicketTime / 1000000)) / 1000) + "S");
                    wait(15000 - (sellTicketTime / 1000000));
                    this.tickets.add(1);
                    config.setTotalTickets(tickets.size());
                    System.out.println("\nVendor" + VendorID + "\nadded ticket....\nAvailable tickets : " + config.getTotalTickets());
                    logger.saveLog("Vendor" + VendorID + "\nadded ticket....\nAvailable tickets : " + config.getTotalTickets());
                    sellTicketNo = 1;
                    sellTicketTime = 0;
                    notifyAll();
                } catch (InterruptedException e) {
                    logger.saveError(String.valueOf(e));
                    throw new RuntimeException(e);
                }
            }
        } else if (tickets.size() >= config.getMaxTicketCapacity()) {
            System.out.println("\nVendor" + VendorID + "\nTicket pool is full......\nAvailable tickets : " + config.getTotalTickets());
            logger.saveWarning("Vendor" + VendorID + "\nTicket pool is full......\nAvailable tickets : " + config.getTotalTickets());
            num = 0;
        }
        return num;
    }

    /**
     * Removes tickets from the pool, adhering to the customer retrieval rate.
     *
     * @param BuyerID ID of the buyer requesting the ticket.
     * @return 1 if the ticket was removed successfully, 0 otherwise.
     */
    public synchronized int removeTickets(int BuyerID) {
        int num = 1;
        Random rand = new Random();
        int ranTime = rand.nextInt(2000); // Random delay time
        long starTime = System.nanoTime();
        try {
            if (buyTicketNo < config.getCustomerRetrievalRate()) {
                try {
                    Thread.sleep(ranTime); // Simulate processing delay
                } catch (InterruptedException e) {
                    logger.saveError(String.valueOf(e));
                    throw new RuntimeException(e);
                }
                this.tickets.remove(0);
                config.setTotalTickets(tickets.size());
                System.out.println("\nBuyer" + BuyerID + "\nbought ticket....\nAvailable tickets : " + config.getTotalTickets());
                logger.saveLog("Buyer" + BuyerID + "\nbought ticket....\nAvailable tickets : " + config.getTotalTickets());
                buyTicketNo++;
                buyTicketTime += (System.nanoTime() - starTime);
            } else if (buyTicketNo > config.getCustomerRetrievalRate()) {
                try {
                    System.out.println("\nBuyer" + BuyerID + "\nRetrieval rate exceeded....\nWaiting - " + ((15000 - (buyTicketTime / 1000000)) / 1000) + "S");
                    logger.saveWarning("Buyer" + BuyerID + "\nRetrieval rate exceeded....\nWaiting - " + ((15000 - (buyTicketTime / 1000000)) / 1000) + "S");
                    wait(15000 - (buyTicketTime / 1000000));

                    this.tickets.remove(0);
                    config.setTotalTickets(tickets.size());
                    System.out.println("\nBuyer" + BuyerID + "\nTicket bought...\nAvailable tickets : " + config.getTotalTickets());
                    logger.saveLog("Buyer" + BuyerID + "\nbought ticket....\nAvailable tickets : " + config.getTotalTickets());
                    buyTicketNo = 1;
                    buyTicketTime = 0;
                    notifyAll();
                } catch (InterruptedException e) {
                    logger.saveError(String.valueOf(e));
                    throw new RuntimeException(e);
                }

            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("\nBuyer" + BuyerID + "\nNo ticket available....\nAvailable tickets : " + config.getTotalTickets());
            logger.saveWarning("Buyer" + BuyerID + "\nNo ticket available....\nAvailable tickets : " + config.getTotalTickets());
            num = 0;
        }
        return num;
    }
}
