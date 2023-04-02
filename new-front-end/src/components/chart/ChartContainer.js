import React, {useState} from 'react';
import axios from 'axios';
import Chart from './Chart';
import ChartSelectionForm from "./ChartSelectionForm";
import {Button} from "@chakra-ui/react";

// Chart container component
function ChartContainer() {
    const username = localStorage.getItem('user');
    const [data, setData] = useState(null);
    const [value, setValue] = useState('');

    const handleSubmit = (event, value) => {
        event.preventDefault();
        setValue(value);
        // fetch data for selected chart type and render chart
        axios.get(`http://localhost:8080/data?username=${username}`)
            .then(response => {
                // render chart with data
                console.log("here3");
                console.log(response.data);
                setData(response.data);
            });
    };

    return (
        <>
            <Button color="teal">
                Submit
            </Button>
            <div >
                <ChartSelectionForm onSubmit={handleSubmit}/>
                {data ? <Chart data={data} value={value} log={null}/> : null}
            </div>
        </>
    );
}

export default ChartContainer;