import {
    BarChart,
    Bar,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    Legend,
    PieChart,
    Pie, ResponsiveContainer,
} from 'recharts';
import React from 'react';
import {useWindowSize} from "react-use";

const username = localStorage.getItem("user");
function renderChart(chartType, chartData) {

    if (chartType === 'bar') {
        return (
            <ResponsiveContainer width="100%" height="100%">
                <BarChart
                    data={chartData}
                    margin={{ top: 30, right: 20, left: 5, bottom: 0 }}
                >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Bar dataKey="amount" fill="#618d8d" />
                </BarChart>
            </ResponsiveContainer>
        );
    }
    if (chartType === 'pie') {
        return (
            <ResponsiveContainer width="100%" height="100%">
                <PieChart >
                    <Pie
                        data={chartData}
                        dataKey="amount"
                        cx={'50%'}
                        cy={'50%'}
                        innerRadius={20}
                        outerRadius={200}
                        fill="#618d8d"
                        label
                    />
                </PieChart>
            </ResponsiveContainer>
        );
    }
}

function ChartRender({ data, chartType, log}) {
    const { width, height } = useWindowSize();

    const chartData = [
        {
            name: 'User Amount',
            amount: 10,
        },
        {
            name: 'Total Amount',
            amount: 1000,
        },
    ];

    return (
        renderChart(chartType, chartData)
    );
}

export default ChartRender;