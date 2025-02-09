package com.spring.springoopcw.Controller;

import com.spring.springoopcw.Configuration.Configuration;
import com.spring.springoopcw.Logs.Logger;
import org.springframework.web.bind.annotation.*;

import static com.spring.springoopcw.Service.TicketService.loadFromJason;

/**
 * ConfigController is a REST controller that provides endpoints for managing
 * the configuration of the ticketing system. The configuration includes various
 * parameters like total tickets, ticket release rate, and customer retrieval rate.
 * It provides methods to save and load configuration from a JSON file.
 */
@RestController
@RequestMapping("/admin")
public class ConfigController {

    // Logger instance to log the actions performed
    Logger logger = new Logger();

    /**A method to get the cofig object from the frontend.
     * @param config The Configuration object to be saved.
     * @return A success message indicating the configuration was saved.
     */
    @CrossOrigin(origins = "http://localhost:5173/")  // Allow cross-origin requests from localhost:5173
    @PostMapping("/setConfig")
    public String addConfig(@RequestBody Configuration config) {
        // Save the configuration to the specified file path
        config.saveToJason("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\Configuration\\config.json");
        // Log the successful save action
        System.out.println("Configuration file saved successfully....");
        System.out.println(config.toString());
        logger.saveLog("Configuration file saved successfully....");
        return "success";  // Return a success message
    }

    /**A method return the config object to the front end.
     * @return The current Configuration object.
     */
    @CrossOrigin(origins = "http://localhost:5173/")  // Allow cross-origin requests from localhost:5173
    @GetMapping("/getConfig")
    public Configuration returnConfig() {
        // Load the configuration from the specified JSON file path
        Configuration config = loadFromJason("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\Configuration\\config.json");
        return config;  // Return the loaded configuration
    }

}
