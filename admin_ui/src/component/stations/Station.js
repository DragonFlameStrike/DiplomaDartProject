import React from 'react';

const Station = ({ station, currentStation }) => {

    const redirectToEvents = (id) => {
        fetch(`http://localhost:8080/api/signal/config/set-current-station/${id}`)
        window.location.href = `/events/`;
    };

    const buttonStyle = {
        width: '60%',
        height: '150px',
        borderRadius: '10px',
        border: '2px solid',
        borderColor: 'black',
        fontSize: '24px',
        backgroundColor: currentStation === station.id ? '#587cf3' : 'white', // Условие для изменения цвета кнопки
        color: currentStation === station.id ? 'white' : 'black', // Условие для изменения цвета текста кнопки
        textAlign: 'center',
        cursor: 'pointer',
    };

    const containerStyle = {
        width: '100%',
        display: 'flex',
        flexWrap: 'wrap',
        justifyContent: 'center',
        marginTop: '20px'
    };

    const stationInfoStyle = {
        display: 'table',
        width: '100%',
        textAlign: 'center'
    };

    const rowDataStyle = {
        display: 'table-row'
    };

    const cellStyle = {
        display: 'table-cell',
        padding: '10px'
    };

    return (
        <div>
            <div style={containerStyle}>
                <button style={buttonStyle} onClick={() => redirectToEvents(station.id)}>
                    <div style={stationInfoStyle}>
                        <div style={rowDataStyle}>
                            <div style={cellStyle}>{station.station}</div>
                        </div>
                        <div style={rowDataStyle}>
                            <div style={cellStyle}>Число событий: {station.eventsCount}</div>
                        </div>
                    </div>
                </button>
            </div>
        </div>
    );
};

export default Station;
