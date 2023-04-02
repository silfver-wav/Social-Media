import React, {useState} from "react";

import axios from "axios";
import ChartSelectionForm from "./ChartSelectionForm";
import {Divider, Stack, Paper} from "@mantine/core";
import {useWindowSize} from "react-use";
import ChartRender from "./ChartRender";


const mockData = {
    amount: 200,
    totalAmount: 1500,
};

export default function ChartCon () {
    const { width, height } = useWindowSize();
    const [data, setData] = useState(null);
    const [value, setValue] = useState('');

    const handleSubmit = (event, value) => {
        event.preventDefault();
        setData(mockData);
        setValue(value);
        /*
        setValue(value);
        // fetch data for selected chart type and render chart
        axios.get(`http://localhost:8080/data?username=${username}`)
            .then(response => {
                // render chart with data
                console.log("here3");
                console.log(response.data);
                setData(response.data);
            });

         */
    };

    return (
        <Stack justify="flex-end" spacing="sm" sx={(theme) => ({ height: height-27 })}>
            {data ?
                <Paper style={{ width: '100%', height: '100%'}} shadow="sm" p="lg">
                    <ChartRender data={data} chartType={value} log={null}/>
                </Paper>
                    : null}

            <Divider my="sm" />
            <ChartSelectionForm onSubmit={handleSubmit}/>
        </Stack>
    );
}