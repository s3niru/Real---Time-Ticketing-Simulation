import Core.TicketPool;
import Configuration.Configuration;
import Logs.Logger;
import Vendor.Vendor;
import Customer.Customer;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The main entry point for the application.
 * It manages the configuration, ticket pool, vendor, and customer operations,
 * allowing users to either create a new configuration file or load an existing one.
 */
public class Main {
    public static void main(String[] args){
        // Initialize necessary objects
        Configuration config = new Configuration();
        List<Integer> tickets = new LinkedList<>();
        Logger logger = new Logger();
        logger.deleteFiles();  // Delete old log files before starting a new session
        Scanner input = new Scanner(System.in);

        // Ask user whether they want to create a new configuration or load an existing one
        System.out.println("Do you want create a new configuration file? (y/n) :");
        String answer = input.nextLine();

        while(true){
            if (answer.equals("y")){  // If user wants to create a new configuration
                promForConfiguration(config);  // Prompt user for configuration inputs
                config.saveToJason("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\Configuration\\config.json");// Save configuration to file
                System.out.println(config.toString());
                break;
            } else if (answer.equals("n")){  // If user wants to load an existing configuration
                try {
                    config = loadFromJason("C:\\Users\\senir\\OneDrive\\IIT - L5\\OOP\\CW\\OOP_CW_(w2051618\\Configuration\\config.json");  // Load configuration
                    System.out.println("Loading previous configuration file...");
                    System.out.println(config.toString());
                    logger.saveLog("Loaded previous configuration file.");
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Failed to load previous configuration file.");
                    logger.saveError("Failed to load previous configuration file.");
                }
            } else {
                System.out.println("Enter a valid choice...");
                System.out.println("Do you want create a new configuration file? (y/n) :");
                input.nextLine();
            }
        }

        // Random number generator for vendors and customers
        Random rand = new Random();
        TicketPool ticketPool = new TicketPool(tickets);

        // Add tickets to the pool based on the configuration
        for (int i = 0; i < config.getTotalTickets(); i++){
            tickets.add(1);
        }

        // Create and start vendor threads
        for (int i = 0; i < config.getNoOfVendors(); i++){
            Vendor vendor = new Vendor(ticketPool, i + 1, rand.nextInt(1, 10));
            vendor.start();
        }

        // Create and start customer threads
        for (int i = 0; i < config.getNoOfCustomers(); i++){
            Customer customer = new Customer(ticketPool, i + 1, rand.nextInt(1, 10));
            customer.start();
        }
    }

    /**
     * Prompts the user to enter configuration values such as ticket count, vendor count, etc.
     * This method ensures the entered values are valid.
     *
     * @param config The configuration object to store the user inputs.
     */
    public static void promForConfiguration(Configuration config) {
        Logger logger = new Logger();
        Scanner input = new Scanner(System.in);

        // Prompt user for total tickets
        while (true) {
            System.out.println("Enter the total Number of tickets : ");
            try {
                config.setTotalTickets(input.nextInt());
                if (config.getTotalTickets() < 0) {
                    System.out.println("Please enter a positive number !");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                logger.saveError(String.valueOf(e));
                System.out.println("Enter a valid number !");
                input.nextLine();
            }
        }

        // Prompt user for ticket release rate
        while (true) {
            System.out.println("Enter the Ticket Release Rate : ");
            try {
                config.setTicketReleaseRate(input.nextInt());
                if (config.getTicketReleaseRate() < 0) {
                    System.out.println("Please enter a positive number !");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                logger.saveError(String.valueOf(e));
                System.out.println("Please enter a positive number !");
                input.nextLine();
            }
        }

        // Prompt user for customer retrieval rate
        while (true) {
            System.out.println("Enter the Customer Retrieval Rate : ");
            try {
                config.setCustomerRetrievalRate(input.nextInt());
                if (config.getCustomerRetrievalRate() < 0) {
                    System.out.println("Please enter a positive number !");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                logger.saveError(String.valueOf(e));
                System.out.println("Please enter a positive number !");
                input.nextLine();
            }
        }

        // Prompt user for maximum ticket capacity
        while (true) {
            System.out.println("Enter the Maximum Ticket Capacity : ");
            try {
                config.setMaxTicketCapacity(input.nextInt());
                if (config.getMaxTicketCapacity() < 0) {
                    System.out.println("Please enter a positive number !");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                logger.saveError(String.valueOf(e));
                System.out.println("Please enter a positive number !");
                input.nextLine();
            }
        }

        // Prompt user for number of vendors
        while (true) {
            System.out.println("Enter the No of Vendors : ");
            try {
                config.setNoOfVendors(input.nextInt());
                if (config.getNoOfVendors() < 0) {
                    System.out.println("Please enter a positive number !");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                logger.saveError(String.valueOf(e));
                System.out.println("Please enter a positive number !");
                input.nextLine();
            }
        }

        // Prompt user for number of customers
        while (true) {
            System.out.println("Enter the No of Customers : ");
            try {
                config.setNoOfCustomers(input.nextInt());
                if (config.getNoOfCustomers() < 0) {
                    System.out.println("Please enter a positive number !");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                logger.saveError(String.valueOf(e));
                System.out.println("Please enter a positive number !");
                input.nextLine();
            }
        }

        // Log and confirm configuration saving
        logger.saveLog("Configuration saved...");
        System.out.println("Configuration has been saved.....");
        input.close();
    }

    /**
     * Loads the configuration from a JSON file.
     *
     * @param filepath The file path of the configuration file.
     * @return The loaded Configuration object.
     */
    public static Configuration loadFromJason(String filepath) {
        Logger logger = new Logger();
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filepath)) {
            // Read and parse the configuration from the JSON file
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            // Log any errors and return null if file loading fails
            e.printStackTrace();
            logger.saveError(String.valueOf(e));
            return null;
        }
    }
}
