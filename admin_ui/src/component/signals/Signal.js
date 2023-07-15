import React from 'react';
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


class Signal extends React.Component {
    options;
    data;
    state = {
        isLoading: true,
    };

    componentDidMount() {
        if (!Array.isArray(this.props.signal.measurements)) {
            setTimeout(() => {
                this.setState({ isLoading: false });
            }, 1000);
        } else {
            this.setState({ isLoading: false });
        }
    }

    render() {
        if (this.state.isLoading) {
            // Отображение загрузочного состояния, например, спиннера или сообщения о загрузке
            return <div>Loading...</div>;
        }
        let labels = [];
        let data = [];

        if (Array.isArray(this.props.signal.measurements)) {
            let indent = Math.floor(this.props.signal.measurements.length / 10);
            let isHeightInMillimeters = false; // Переменная для отслеживания умножения высоты на 1000
            for (let i = indent; i < this.props.signal.measurements.length - indent; i++) {
                const point = this.props.signal.measurements[i];

                const date = new Date(point.time);
                const label = `${date.toLocaleDateString()} ${date.toLocaleTimeString()}`;

                let height = point.height;
                if (height < 1) {
                    height *= 1000;
                    isHeightInMillimeters = true; // Высота была умножена на 1000
                }

                labels.push(label);
                data.push(height);
            }


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
                            label: isHeightInMillimeters ? 'Высота в миллиметрах' : 'Высота в метрах',
                            data,
                            fill: false,
                            pointRadius: 1,
                            borderColor: "rgba(255,0,0,1)",
                        },
                    ],
                };

        }

        return(
            <div style={{ backgroundColor: 'rgba(255, 255, 255, 0.7)' }}>
                <Line options={this.options} data={this.data} />
            </div>
        )
    }
}

export default Signal;