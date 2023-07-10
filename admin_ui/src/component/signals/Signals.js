import { useState, useEffect } from 'react';
import axios from 'axios';
import Signal from "./Signal";


const Signals = (props) => {
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
    }, []);

    return(
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '100px' , justifyContent: 'center', alignItems: 'center'}}>
            <div style={{ flexBasis: 'calc(40% - 20px)' }}>
                <Signal signal={mainSignal} name = {"Входной сигнал"}/>
            </div>
            <div style={{ flexBasis: 'calc(40% - 20px)' }}>
                <Signal signal={extrapolatedSignal} name = {"Подготовленный к фильрации сигнал"}/>
            </div>
            <div style={{ flexBasis: 'calc(40% - 20px)' }}>
                <Signal signal={filteredSignal} name = {"Отфильтрованный сигнал"}/>
            </div>
            <div style={{ flexBasis: 'calc(40% - 20px)' }}>
                <Signal signal={tsunamiSignal} name = {"Цунами компонента"}/>
            </div>
        </div>
    );
};

export default Signals;
