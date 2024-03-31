import Signal from "../signals/Signal";
import React, { useState, useEffect } from 'react';
import axios from "axios";
import CompareSignal from "../signals/CompareSignal";

const Featured = () => {

    const [firstStationName, setFirstStationName] = useState('')
    const [firstEventDate, setFirstEventDate] = useState('')

    const [secondStationName, setSecondStationName] = useState('')
    const [secondEventDate, setSecondEventDate] = useState('')

    const [firstMainSignal, setFirstMainSignal] = useState([]);
    const [firstFilteredSignal, setFirstFilteredSignal] = useState([]);
    const [secondMainSignal, setSecondMainSignal] = useState([]);
    const [secondFilteredSignal, setSecondFilteredSignal] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/api/signal/featured')
            .then(response => {
                console.log(response.data)

                setFirstMainSignal(response.data.firstMainSignal);
                setFirstFilteredSignal(response.data.firstFilteredSignal);
                setSecondMainSignal(response.data.secondMainSignal);
                setSecondFilteredSignal(response.data.secondFilteredSignal);

                setFirstStationName(response.data.firstStationName);
                setFirstEventDate(response.data.firstEventDate);
                setSecondStationName(response.data.secondStationName);
                setSecondEventDate(response.data.secondEventDate);

                console.log(firstMainSignal)
                console.log(firstFilteredSignal)
                console.log(secondMainSignal)
                console.log(secondFilteredSignal)
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    return (
        <div>
            <div>
                1. {firstStationName}-{firstEventDate}
            </div>
            <div>
               2. {secondStationName}-{secondEventDate}
            </div>
            <div style={{
                display: 'flex',
                flexWrap: 'wrap',
                gap: '50px',
                justifyContent: 'center',
                alignItems: 'center'
            }}>
                <div style={{flexBasis: 'calc(80% - 20px)'}}>
                    <CompareSignal signal1={firstFilteredSignal} signal2={secondFilteredSignal} name={"Результат сравнения"}/>
                </div>
                <div style={{flexBasis: 'calc(40% - 20px)'}}>
                    <Signal signal={firstMainSignal} name={"Первый не отфильтрованный сигнал"}/>
                </div>
                <div style={{flexBasis: 'calc(40% - 20px)'}}>
                    <Signal signal={secondMainSignal} name={"Второй не отфильтрованный сигнал"}/>
                </div>
                <div style={{flexBasis: 'calc(40% - 20px)'}}>
                    <Signal signal={firstFilteredSignal} name={"Первый отфильтрованный сигнал"}/>
                </div>
                <div style={{flexBasis: 'calc(40% - 20px)'}}>
                    <Signal signal={secondFilteredSignal} name={"Второй отфильтрованный сигнал"}/>
                </div>
            </div>
        </div>
    )
}

export default Featured