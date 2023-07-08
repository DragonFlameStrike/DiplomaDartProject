import { useState, useEffect } from 'react';
import axios from 'axios';
import Signal from "./Signal";


const Signals = (props) => {
    const [signals, setSignals] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/api/signal/filter')
            .then(response => {
                setSignals(response.data);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);


    const sections = Array.from({ length: 4}).map((_, index) => (
        <div  key={index}>
            <Signal/>
        </div>
    ));

    return(
        <section className="content">
            {sections}
        </section>
    );
};

export default Signals;
