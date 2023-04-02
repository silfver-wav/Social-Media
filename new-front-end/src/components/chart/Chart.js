import {
    BarChart,
    Bar,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    Legend,
    PieChart,
    Pie,
} from 'recharts';
import React from 'react';

const username = localStorage.getItem("user");
function renderChart(chartType, chartData) {
    if (chartType === 'bar') {
        console.log("bar");
        return (
            <div>
                <BarChart
                    width={1000}
                    height={500}
                    data={chartData}
                    margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
                >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Bar dataKey="amount" fill="#618d8d" />
                </BarChart>
            </div>
        );
    }
    if (chartType === 'pie') {
        console.log("pie");
        return (
            <div >
                <PieChart width={1000} height={500}>
                    <Pie
                        data={chartData}
                        dataKey="amount"
                        cx={200}
                        cy={200}
                        innerRadius={20}
                        outerRadius={150}
                        fill="#618d8d"
                        label
                    />
                </PieChart>
            </div>
        );
    }
}

function Chart({ data, chartType, log}) {
    const data1 = data.amount;
    const data2 = data.totalAmount;

    const chartData = [
        {
            name: 'User Amount',
            amount: data.amount,
        },
        {
            name: 'Total Amount',
            amount: data.totalAmount,
        },
    ];

    console.log("type: " + chartType);

    const handleSubmit = async (e) => {
        e.preventDefault();

        const data = JSON.stringify({
            type: "chart",
            userName: username,
            chartType: chartType,
            amount: data1,
            totalAmount: data2,
        })

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: data
        };

        console.log(data);

        fetch("http://localhost:8082/personal_log/add", requestOptions)
            .then((res) => {
                console.log(res);
            }).catch((err) => {
            console.log("error" + err);
        })

    }

    return (
        <div >
            {renderChart(chartType, chartData)}
            {log ? null : <button type="submit" onClick={handleSubmit} >Post</button>}
        </div>
    );
}

export default Chart;
