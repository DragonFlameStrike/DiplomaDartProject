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
import {Line} from 'react-chartjs-2';


ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
);


class CompareSignal extends React.Component {
    options;
    data;
    state = {
        isLoading: true,
    };

    componentDidMount() {
        if (!Array.isArray(this.props.signal1.measurements) || !Array.isArray(this.props.signal2.measurements)) {
            setTimeout(() => {
                this.setState({isLoading: false});
            }, 2000);
        } else {
            this.setState({isLoading: false});
        }
    }

    render() {
        if (this.state.isLoading) {
            return <div>Loading...</div>;
        }
        let labels = [];
        let data1 = [];
        let data2 = [];

        if (Array.isArray(this.props.signal1.measurements) && Array.isArray(this.props.signal2.measurements)) {
            let indent1 = Math.floor(this.props.signal1.measurements.length / 15);
            let indent2 = Math.floor(this.props.signal2.measurements.length / 15);
            let firstSignalTimeStart = this.props.signal1.measurements[this.props.signal1.measurements.length - 1 - indent1].time
            let secondSignalTimeStart = this.props.signal2.measurements[this.props.signal2.measurements.length - 1 - indent2].time
            let firstSignalTimeEnd = this.props.signal1.measurements[indent1].time
            let secondSignalTimeEnd = this.props.signal2.measurements[indent2].time

            // Найти общий временной интервал
            let startTime = Math.min(
                new Date(firstSignalTimeStart),
                new Date(secondSignalTimeStart)
            );
            let endTime = Math.max(
                new Date(firstSignalTimeEnd),
                new Date(secondSignalTimeEnd)
            );

            // Создать массив меток времени
            let currentTime = new Date(startTime);
            while (currentTime <= endTime) {
                labels.push(currentTime.toLocaleTimeString());
                currentTime.setSeconds(currentTime.getSeconds() + 15); // Увеличиваем время на 15 секунд
            }

            data1.push(0);
            for (let i = this.props.signal1.measurements.length - indent1 - 1; i >= indent1; i--) {
                const point = this.props.signal1.measurements[i];

                let height = point.height;
                if (height < 1) {
                    height *= 1000;
                }
                data1.push(height);

                if (point.time < secondSignalTimeStart) {
                    data2.push(null)
                }
            }
            data2.push(0);
            data1.push(0);
            for (let i = this.props.signal2.measurements.length - indent2 - 1; i >= indent2; i--) {
                const point = this.props.signal2.measurements[i];

                let height = point.height;
                if (height < 1) {
                    height *= 1000;
                }
                data2.push(height);

                if (point.time > firstSignalTimeStart) {
                    data1.push(null)
                }
            }
            data2.push(0);
            this.options = {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top',
                    },
                    title: {
                        display: false,
                        text: this.props.name,
                    },
                },
            };

            this.data = {
                labels,
                datasets: [
                    {
                        label: 'Высота в милиметрах (Сигнал 1)',
                        data: data1,
                        fill: false,
                        pointRadius: 1,
                        borderColor: "rgba(255,0,0,1)",
                    },
                    {
                        label: 'Высота в милиметрах (Сигнал 2)',
                        data: data2,
                        fill: false,
                        pointRadius: 1,
                        borderColor: "rgba(0,0,255,1)",
                    },
                ],
            };
        }

        return (
            <div style={{
                backgroundColor: 'rgba(255, 255, 255, 0.7)',
                textAlign: 'center', // Выравнивание текста по центру
                padding: '20px' // Добавляем отступы для улучшения визуального вида
            }}>
                <h4 style={{margin: 0}}>{this.props.name}</h4>
                <Line options={this.options} data={this.data} type={"line"}/>
            </div>
        )
    }
}

export default CompareSignal;