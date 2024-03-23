import React, { useState, useEffect } from 'react';
import Event from './Event';

const Events = () => {
    const [events, setEvents] = useState([]);
    const [currentEvent, setCurrentEvent] = useState([]);

    useEffect(() => {
        fetchEvents();
    }, []);

    const fetchEvents = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/signal/events');
            const data = await response.json();
            setEvents(data.events);

            const currentStationResponse = await fetch('http://localhost:8080/api/signal/stations/get-config');
            const config = await currentStationResponse.json();
            setCurrentEvent(config.currentEvent)
        } catch (error) {
            console.error('Error fetching stations:', error);
        }
    };

    const pageTitleStyle = {
        fontSize: '36px',
        fontWeight: 'bold',
        textAlign: 'center',
        marginRight: '10%',
    };

    const boxStyle = {
        width: '80%',
        marginLeft: '16%',
    }

    return (
        <div style={boxStyle}>
            <h2 style={pageTitleStyle}>Список событий</h2>
            <ul style={{
                display: 'flex',
                flexWrap: 'wrap',
                listStyleType: 'none',
            }}>
                {events.map((event, index) => (
                    <li key={index}>
                        <Event event={event} currentEvent={currentEvent} />
                    </li>
                ))}
            </ul>

        </div>
    );
};

export default Events;
