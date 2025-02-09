package com.spring.springoopcw.Controller;

import com.spring.springoopcw.Logs.Logger;
import com.spring.springoopcw.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TicketController is a REST controller that provides endpoints for starting and stopping
 * the ticketing system. It interacts with the TicketService to manage the system's operation
 * and logs actions related to system start and stop.
 */
@RestController
@RequestMapping("/api")
public class TicketController {

    // Autowired TicketService to manage ticket-related functionality
    @Autowired
    private final TicketService ticketPoolService = new TicketService();

    // Logger instance to log the actions performed
    Logger logger = new Logger();

    /**
     * @return A success message indicating that the program has started.
     */
    @PostMapping("/start")
    public String startSystem() {
        // Start the ticketing system using the TicketService
        ticketPoolService.startSystem();
        // Log the start action
        logger.saveLog("Program Has Started....");
        return "Program started";  // Return a success message
    }

    /**
     * @return A success message indicating that the program has stopped.
     */
    @PostMapping("/stop")
    public String stopSystem() {
        // Stop the ticketing system using the TicketService
        ticketPoolService.stopSystem();
        // Log the stop action
        logger.saveLog("Program Has Stopped.....");
        return "Program stopped";  // Return a success message
    }
}
