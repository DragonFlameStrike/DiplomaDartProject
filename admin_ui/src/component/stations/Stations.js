import React, { useState, useEffect } from 'react';
import Station from './Station'; // Импорт компонента Station из файла Station.js

const Stations = () => {
    const [stations, setStations] = useState([]);
    const [currentStation, setCurrentStation] = useState([]);

    useEffect(() => {
        fetchStations();
    }, []);

    const fetchStations = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/signal/stations');
            const data = await response.json();
            setStations(data.stations);

            const currentStationResponse = await fetch('http://localhost:8080/api/signal/stations/get-config');
            const config = await currentStationResponse.json();
            setCurrentStation(config.currentStation)
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
                    <Station key={index} station={station} currentStation={currentStation} /> // Передача станции как props
                ))}
            </ul>
        </div>
    );
};

export default Stations;
