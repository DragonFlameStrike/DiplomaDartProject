import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Events = () => {
    const [events, setEvents] = useState([]);
    const [selectedEvent, setSelectedEvent] = useState(null); // Store the selected event

    useEffect(() => {
        axios.get('http://localhost:8080/api/signal/parse')
            .then(response => {
                setEvents(response.data.events);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    useEffect(() => {
        if (!Array.isArray(events)) {
            const timeout = setTimeout(() => {
                setEvents([]); // Set events to an empty array after 5 seconds
            }, 5000);
            return () => clearTimeout(timeout);
        }
    }, [events]);

    const handleEventSelection = (event) => {
        setSelectedEvent(event); // Update the selected event
    };

    const handleFilter = () => {
        if (selectedEvent) {
            // Send POST request with the selected event
            axios.post('http://localhost:8080/api/signal/filter', {seriestime : selectedEvent.seriestime, date: selectedEvent.date})
                .then(response => {
                    // Handle the response if needed
                    console.log(response.data);
                    window.location.href = `http://localhost:3000/?event=${selectedEvent.date}`
                })
                .catch(error => {
                    console.log(error);
                });
        }
    };

    return (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
            {Array.isArray(events) ? (
                <div >
                    <h3>Events:</h3>
                    {events.map(event => (
                        <div key={event.seriestime} >
                            <input
                                type="radio"
                                name="event"
                                value={event.seriestime}
                                checked={selectedEvent === event}
                                onChange={() => handleEventSelection(event)}
                            />
                            <label>{event.date}</label>
                        </div>
                    ))}
                    <button onClick={handleFilter} disabled={!selectedEvent} >Filter</button>
                </div>
            ) : (
                <div>
                    <p>Loading events...</p>
                </div>
            )}
        </div>
    );
};
export default Events;
