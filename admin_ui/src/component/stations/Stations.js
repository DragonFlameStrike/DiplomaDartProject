import React, { useState, useEffect } from 'react';
import Station from './Station'; // Импорт компонента Station из файла Station.js

const Stations = () => {
    const [stations, setStations] = useState([]);

    useEffect(() => {
        fetchStations();
    }, []);

    const fetchStations = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/signal/stations');
            const data = await response.json();
            setStations(data.stations);
        } catch (error) {
            console.error('Error fetching stations:', error);
        }
    };

    const pageTitleStyle = {
        fontSize: '36px',
        fontWeight: 'bold',
        textAlign: 'center'
    };

    return (
        <div>
            <h2 style={pageTitleStyle}>Список станций</h2>
            <ul>
                {stations.map((station, index) => (
                    <Station key={index} station={station} /> // Передача станции как props
                ))}
            </ul>
        </div>
    );
};

export default Stations;
