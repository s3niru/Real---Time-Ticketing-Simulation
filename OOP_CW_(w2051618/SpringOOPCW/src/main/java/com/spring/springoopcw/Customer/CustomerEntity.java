package com.spring.springoopcw.Customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * CustomerEntity is a JPA entity representing a customer in the ticketing system.
 * It includes the customer's unique ID, their customer ID, and the number of tickets they have purchased.
 */
@Entity
public class CustomerEntity {

    // The unique ID of the customer, auto-generated by the database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int customerId;
    private int ticketsBought;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getTicketsBought() { return ticketsBought; }
    public void setTicketsBought(int ticketsBought) { this.ticketsBought = ticketsBought; }

}

