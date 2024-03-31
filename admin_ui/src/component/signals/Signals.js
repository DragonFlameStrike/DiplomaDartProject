import {useState, useEffect} from 'react';
import axios from 'axios';
import Signal from "./Signal";


const Signals = () => {
    const [stationName, setStationName] = useState('')
    const [eventDate, setEventDate] = useState('')

    const [tsunamiSignal, setTsunamiSignal] = useState([]);
    const [mainSignal, setMainSignal] = useState([]);
    const [extrapolatedSignal, setExtrapolatedSignal] = useState([]);
    const [filteredSignal, setFilteredSignal] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/api/signal/filter')
            .then(response => {
                setMainSignal(response.data.mainSignal[0]);
                setTsunamiSignal(response.data.tsunamiSignal[0]);
                setExtrapolatedSignal(response.data.extrapolatedSignal[0]);
                setFilteredSignal(response.data.filteredSignal[0]);
            })
            .catch(error => {
                console.log(error);
            });

        axios.get('http://localhost:8080/api/signal/config/get-config')
            .then(response => {
                setStationName(response.data.currentStationName);
                setEventDate(formatDate(response.data.currentEventDate));
            })
            .catch(error => {
                console.log(error);
            });

    }, []);

    const formatDate = (dateString) => {
        // Разбиваем строку на части
        const year = dateString.substring(0, 4);
        const month = dateString.substring(4, 6);
        const day = dateString.substring(6, 8);
        const hours = dateString.substring(8, 10);
        const minutes = dateString.substring(10, 12);

        // Собираем новую строку в формате "год-месяц-день, часы:минуты"
        return `${year}-${month}-${day}, ${hours}:${minutes}`;
    };

    const saveSignal = (extrapolatedSignal, tsunamiSignal, stationName, eventDate) => {
        // Создаем объект с данными для отправки
        const data = {
            extrapolatedSignal: extrapolatedSignal,
            tsunamiSignal: tsunamiSignal,
            stationName: stationName,
            eventDate: eventDate
        };

        // Отправляем POST-запрос
        axios.post('http://localhost:8080/api/signal/save', data)
            .then(response => {
                console.log('Успешно сохранено:', response.data);
            })
            .catch(error => {
                console.error('Ошибка при сохранении:', error);
            });
    };

    return (
        <div>
            <section style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                marginBottom: '20px' // добавляем небольшой отступ между шапкой и сигналами
            }}>
                <h2>{stationName} - {eventDate}</h2>
                <button style={{marginLeft: '10px'}} onClick={() => saveSignal(extrapolatedSignal, tsunamiSignal, stationName, eventDate)}>
                    Добавить в избранное
                </button>
            </section>
            <div>
                <div style={{
                    display: 'flex',
                    flexWrap: 'wrap',
                    gap: '50px',
                    justifyContent: 'center',
                    alignItems: 'center'
                }}>
                    <div style={{flexBasis: 'calc(40% - 20px)'}}>
                        <Signal signal={mainSignal} name={"Входной сигнал"}/>
                    </div>
                    <div style={{flexBasis: 'calc(40% - 20px)'}}>
                        <Signal signal={extrapolatedSignal} name={"Подготовленный к фильрации сигнал"}/>
                    </div>
                    <div style={{flexBasis: 'calc(40% - 20px)'}}>
                        <Signal signal={filteredSignal} name={"Отфильтрованный сигнал"}/>
                    </div>
                    <div style={{flexBasis: 'calc(40% - 20px)'}}>
                        <Signal signal={tsunamiSignal} name={"Цунами компонента"}/>
                    </div>
                </div>
                <div style={{
                    display: 'flex',
                    flexWrap: 'wrap',
                    gap: '100px',
                    justifyContent: 'center',
                    alignItems: 'center',
                    margin: '140px'
                }}>
                    <div style={{flexBasis: 'calc(100% - 20px)'}}>
                        <Signal signal={mainSignal} name={"Входной сигнал"}/>
                    </div>
                    <div style={{flexBasis: 'calc(100% - 20px)'}}>
                        <Signal signal={extrapolatedSignal} name={"Подготовленный к фильрации сигнал"}/>
                    </div>
                    <div style={{flexBasis: 'calc(100% - 20px)'}}>
                        <Signal signal={filteredSignal} name={"Отфильтрованный сигнал"}/>
                    </div>
                    <div style={{flexBasis: 'calc(100% - 20px)'}}>
                        <Signal signal={tsunamiSignal} name={"Цунами компонента"}/>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Signals;
