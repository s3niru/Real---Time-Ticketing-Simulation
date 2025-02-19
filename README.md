# Real---Time-Ticketing-Simulation

## Introduction
The Real-Time Ticketing Event System allows users to manage and monitor ticket availability in real-time. The system includes a ticket pool where tickets can be added and vendors can purchase them. The tech stack comprises Spring Boot for the backend and React for the frontend, with a CLI component developed in pure Java.

## Setup Instructions

### Prerequisites
- **Java**: Version 11 or later
- **Node.js**: Version 14 or later
- **Maven**: For building the Spring Boot application
- **NPM**: For managing frontend dependencies

### Build and Run

#### Backend (Spring Boot)
1. **Clone the Repository**:
    ```bash
    git clone <repository-url>
    cd <repository-folder>
    ```
2. **Build the Backend**:
    ```bash
    mvn clean install
    ```
3. **Run the Backend**:
    - You can hit the run button on your IDE (e.g., IntelliJ IDEA, Eclipse).
    - Alternatively, run the following command in the terminal:
      ```bash
      mvn spring-boot:run
      ```
    The backend will run on `http://localhost:8080`.

#### Frontend (React)
1. **Navigate to the Frontend Directory**:
    ```bash
    cd react-project
    ```
2. **Install Dependencies**:
    ```bash
    npm install
    ```
3. **Run the Frontend**:
    ```bash
    npm run dev
    ```
    The frontend will run on `http://localhost:5173`.

#### CLI Component
1. **Run the CLI**:
    - You can also use the run button on your IDE (e.g., IntelliJ IDEA, Eclipse).
    - Alternatively, run the following command in the terminal:
      ```bash
      filepat\OOP_CW_CI\src> javac main.java
      filepat\OOP_CW_CI\src> java main
      ```

### H2 Database
1. **Access the H2 Database Console**:
    - Open a web browser and navigate to `http://localhost:8080/h2-console`.
    - **JDBC URL**: `jdbc:h2:mem:testdb`
    - **Username**: `sa`
    - **Password**: `databse`

## Usage Instructions

### Configuration and Starting the System
1. **Enter Configuration**:
    - **totalTickets**: Total number of tickets available in the pool.
    - **customerRetrievalRate**: Rate at which customers retrieve tickets.
    - **ticketReleaseRate**: Rate at which tickets are added to the pool.
    - **maxTicketCapacity**: Maximum capacity of the ticket pool.
    - **noOfVendors**: Number of vendors who will buy tickets.
    - **noOfCustomers**: Number of customers retrieving tickets.
2. **Submit Configuration**:
    - Enter the above configuration details and submit.
3. **Start the System**:
    - Press the **Start** button to start all the threads. This will initialize the ticket pool and begin processing ticket retrieval and release.
4. **Stop the System**:
    - Press the **Stop** button to stop all the threads.

### UI Controls
- **Configuration Form**: Input fields for `totalTickets`, `customerRetrievalRate`, `ticketReleaseRate`, `maxTicketCapacity`, `noOfVendors`, and `noOfCustomers`.
- **Start Button**: Begins the ticket processing threads.
- **Stop Button**: Halts the ticket processing threads.
- **Live Ticket View**: Displays the current number of available tickets in real-time.

