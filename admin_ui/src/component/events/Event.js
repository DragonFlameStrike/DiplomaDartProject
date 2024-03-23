import React from 'react';

const Event = ({ event, currentEvent }) => {

    const redirectToEvents = (id) => {
        fetch(`http://localhost:8080/api/signal/stations/set-current-event/${id}`)
        window.location.href = `/`;
    };

    const buttonStyle = {
        width: '200px',
        height: '80px',
        borderRadius: '10px',
        border: '2px solid',
        borderColor: 'black',
        fontSize: '24px',
        backgroundColor: currentEvent === event.id ? '#587cf3' : 'white', // Условие для изменения цвета кнопки
        color: currentEvent === event.id ? 'white' : 'black', // Условие для изменения цвета текста кнопки
        textAlign: 'center',
        cursor: 'pointer',
    };

    const containerStyle = {
        width: '100%',
        display: 'flex',
        flexWrap: 'wrap',
        justifyContent: 'center',
        marginTop: '20px',
        marginLeft: '7px'
    };

    const eventInfoStyle = {
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

    const formatDate = (dateString) => {
        // Разбиваем строку на части
        const year = dateString.substring(0, 4);
        const month = dateString.substring(4, 6);
        const day = dateString.substring(6, 8);

        // Собираем новую строку в формате "год-месяц-день"
        const formattedDate = `${year}-${month}-${day}`;

        return formattedDate;
    };

    const formattedDate = formatDate(event.seriesTime);

    return (
        <div>
            <div style={containerStyle}>
                <button style={buttonStyle} onClick={() => redirectToEvents(event.id)}>
                    <div style={eventInfoStyle}>
                        <div style={rowDataStyle}>
                            <div style={cellStyle}>{formattedDate}</div>
                        </div>
                    </div>
                </button>
            </div>
        </div>
    );
};

export default Event;
