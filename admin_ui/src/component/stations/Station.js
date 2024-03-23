import React from 'react';

const Station = ({ station }) => {

    const redirectToEvents = (stationNumber) => {
        window.location.href = `/events/${stationNumber}`;
    };

    const buttonStyle = {
        width: '60%',
        height: '150px',
        backgroundColor: 'white',
        borderRadius: '10px',
        border: '2px solid red',
        color: 'black',
        fontSize: '24px',
        textAlign: 'center',
        cursor: 'pointer'
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
                <button style={buttonStyle} onClick={() => redirectToEvents(station.station.split('=')[1])}>
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
