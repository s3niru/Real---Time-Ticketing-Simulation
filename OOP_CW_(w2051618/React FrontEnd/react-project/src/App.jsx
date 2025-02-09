import './App.css';
import React, { useState, useEffect } from 'react';
import axios from 'axios';

/**
 * Function to start the system by making a POST request to the backend.
 */
const startSystem = async () => {
  await axios.post('http://localhost:8080/api/start');
};

/**
 * Function to stop the system by making a POST request to the backend.
 */
const stopSystem = async () => {
  await axios.post('http://localhost:8080/api/stop');
};

/**
 * ControlPanel component provides Start and Stop buttons to control the system.
 */
function ControlPanel() {
  return (
    <header>
      {/* Start button triggers the startSystem function when clicked */}
      <button style={{ marginRight: '10px' }} onClick={startSystem}>Start</button>
      {/* Stop button triggers the stopSystem function when clicked */}
      <button onClick={stopSystem}>Stop</button>
    </header>
  );
}

function App() {
  // State hooks to store configuration values
  const [config, setConfig] = useState(null);
  const [totaLTckets, setTotalTickets] = useState('');
  const [maxCapacity, setMaxCapacity] = useState('');
  const [releaseRate, setReleaseRate] = useState('');
  const [retrievalRate, setRetrievalRate] = useState('');
  const [noofVendors, setNoOfVendors] = useState('');
  const [noofCustomers, setNoOfCustomers] = useState('');

  /**
   * useEffect hook to fetch the configuration from the backend every second
   * and update the local state.
   */
  useEffect(() => {
    const fetchConfig = async () => {
      try {
        const response = await axios.get('http://localhost:8080/admin/getConfig');
        setConfig(response.data);
      } catch (error) {
        console.error('Error fetching config:', error);
      }
    };

    fetchConfig();

    // Set interval to fetch the config every second
    const intervalId = setInterval(fetchConfig, 1000);

    // Cleanup interval on component unmount
    return () => clearInterval(intervalId);
  }, []);

  /**
   * handleSubmit function is called when the form is submitted.
   * It sends the updated configuration to the backend.
   */
  const handleSubmit = async (e) => {
    e.preventDefault();
    const newConfig = {
      totalTickets: parseInt(totaLTckets),
      maxTicketCapacity: parseInt(maxCapacity),
      ticketReleaseRate: parseInt(releaseRate),
      customerRetrievalRate: parseInt(retrievalRate),
      noOfVendors: parseInt(noofVendors),
      noOfCustomers: parseInt(noofCustomers),
    };

    try {
      // Send the new configuration to the backend
      await axios.post('http://localhost:8080/admin/setConfig', newConfig);
      setConfig(newConfig);
      alert('Configuration submitted successfully!');
    } catch (error) {
      console.error('Error submitting configuration:', error);
    }
  };

  /**
   * calculateProgress function calculates the progress of available tickets
   * as a percentage of the max ticket capacity.
   */
  const calculateProgress = () => {
    if (!config || !config.maxTicketCapacity) return 0;
    return (config.totalTickets / config.maxTicketCapacity) * 100;
  };

  return (
    <React.Fragment>
      {/* Main title of the application */}
      <h1>Real-Time Ticketing System</h1>

      {/* Form to enter configuration values */}
      <form onSubmit={handleSubmit}>
        <div>
          <label>Total Tickets:</label>
          <input type="number" value={totaLTckets} onChange={(e) => {
            const value = e.target.value;
            if (!value || Number(value) >= 0) {
              setTotalTickets(value);
            }
          }} min="1" required />
        </div>
        <div>
          <label>Ticket Release Rate:</label>
          <input type="number" value={releaseRate} onChange={(e) => {
            const value = e.target.value;
            if (!value || Number(value) >= 0) {
              setReleaseRate(value);
            }
          }} min="1" required />
        </div>
        <div>
          <label>Customer Retrieval Rate:</label>
          <input type="number" value={retrievalRate} onChange={(e) => {
            const value = e.target.value;
            if (!value || Number(value) >= 0) {
              setRetrievalRate(value);
            }
          }} min="1" required />
        </div>
        <div>
          <label>Max Ticket Capacity:</label>
          <input type="number" value={maxCapacity} onChange={(e) => {
            const value = e.target.value;
            if (!value || Number(value) >= 0) {
              setMaxCapacity(value);
            }
          }} min="1" required />
        </div>
        <div>
          <label>Number of Vendors:</label>
          <input type="number" value={noofVendors} onChange={(e) => {
            const value = e.target.value;
            if (!value || Number(value) >= 0) {
              setNoOfVendors(value);
            }
          }} min="1" required />
        </div>
        <div>
          <label>Number of Customers:</label>
          <input type="number" value={noofCustomers} onChange={(e) => {
            const value = e.target.value;
            if (!value || Number(value) >= 0) {
              setNoOfCustomers(value);
            }
          }} min="1" required />
        </div>
        <button type="submit">Enter Configuration</button>
      </form>

      {/* Control Panel component for system start and stop */}
      <ControlPanel />

      {/* Progress bar to show available tickets */}
      <div className="progress-container">
        <h2>Available Tickets</h2>
        <div className="progress-bar">
          <div
            className="progress-bar-fill"
            style={{ width: `${calculateProgress()}%` }}
          >
            <span className="progress-label">
              {config ? config.totalTickets : 'Loading...'}
            </span>
          </div>
        </div>
      </div>
    </React.Fragment>
  );
}

export default App;
