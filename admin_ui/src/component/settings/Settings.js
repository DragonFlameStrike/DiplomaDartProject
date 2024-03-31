import React, { useState, useEffect } from 'react';

const Settings = () => {
    const [thresholdFrequency, setThresholdFrequency] = useState('');
    const [aproxyTimes, setAproxyTimes] = useState('');
    const [isSaved, setIsSaved] = useState(false);


    useEffect(() => {
        fetchConfig();
    }, []);

    const fetchConfig = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/signal/config/get-config');
            const data = await response.json();
            setThresholdFrequency(data.thresholdFrequency);
            setAproxyTimes(data.aproxiTimes)
        } catch (error) {
            console.error('Error fetching stations:', error);
        }
    };

    const handleSave = () => {
        fetch(`http://localhost:8080/api/signal/config/set-threshold-frequency/${thresholdFrequency}`)
        setIsSaved(true);
        setTimeout(() => {
            setIsSaved(false);
        }, 2000);
    };

    const handleSaveAproxy = () => {
        fetch(`http://localhost:8080/api/signal/config/set-aproxy-times/${aproxyTimes}`)
        setIsSaved(true);
        setTimeout(() => {
            setIsSaved(false);
        }, 2000);
    };

    const handleUpdateStations = () => {
        fetch(`http://localhost:8080/api/signal/settings/update-stations`)
    };

    const handleAproxyOn = () => {
        fetch(`http://localhost:8080/api/signal/config/set-aproxy-on`)
    };

    const handleAproxyOff = () => {
        fetch(`http://localhost:8080/api/signal/config/set-aproxy-off`)
    };

    const sectionStyle = {
        width: '700px',
        height: '150px',
        borderRadius: '10px',
        border: '2px solid black',
        fontSize: '24px',
        margin: '20px auto', // Центрируем секции по горизонтали и добавляем отступы между ними
        padding: '20px',
        textAlign: 'center' // Выравниваем текст по центру
    };

    const notificationStyle = {
        position: 'fixed',
        bottom: '20px',
        left: '50%',
        transform: 'translateX(-50%)',
        backgroundColor: 'lightgreen',
        border: '2px solid black',
        padding: '10px',
        borderRadius: '5px',
        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
    };

    return (
        <div>
            <div style={sectionStyle}>
                <h3>Изменение глубины фильтраций</h3>
                <input
                    type="text"
                    value={aproxyTimes}
                    onChange={e => setAproxyTimes(e.target.value)}
                    placeholder="Кол-во фильтраций"
                    style={{ marginBottom: '20px' }} // Добавляем отступ после инпута
                />
                <button onClick={handleSaveAproxy}>Сохранить</button>
            </div>

            <div style={sectionStyle}>
                <h3>Изменение пороговой частоты</h3>
                <input
                    type="text"
                    value={thresholdFrequency}
                    onChange={e => setThresholdFrequency(e.target.value)}
                    placeholder="Пороговая частота"
                    style={{ marginBottom: '20px' }} // Добавляем отступ после инпута
                />
                <button onClick={handleSave}>Сохранить</button>
            </div>

            <div style={{ ...sectionStyle, backgroundColor: 'lightgray' }}>
                <button style={{ backgroundColor: 'red', color: 'white', padding: '10px 20px', borderRadius: '5px' }} onClick={handleUpdateStations}>Обновить станции</button>
                <h4>Это займет пару минут*</h4>
            </div>
            {isSaved && (
                <div style={notificationStyle}>
                    <h4>Значение успешно сохранено</h4>
                </div>
            )}
        </div>
    );
};

export default Settings;
