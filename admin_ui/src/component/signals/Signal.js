import axios from 'axios';
import React, { useState, useEffect } from 'react';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
} from 'chart.js';
import { Line } from 'react-chartjs-2';


ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
);


const labels = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];



class Signal extends React.Component {
    options;
    data;
    constructor(props) {
        super(props);
    }

    render() {
        console.log(this.props.signal)
        this.options = {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: this.props.name,
                },
            },
        };
        this.data = {
            labels,
            datasets: [
                {
                    label: 'Dataset 1',
                    data: labels.map(() => 1),
                    borderColor: 'rgb(255, 99, 132)',
                    backgroundColor: 'rgba(255, 99, 132, 0.5)',
                },
            ],
        };
        return(
            <Line options={this.options} data={this.data} />
        )
    }
}
export default Signal;