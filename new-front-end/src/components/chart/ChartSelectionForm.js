import {Button, createStyles, Group, Select, Stack} from "@mantine/core";
import React, {useState} from "react";
import {useWindowSize} from "react-use";

const useStyles = createStyles((theme) => ({
    root: {
        position: 'relative',
    },

    input: {
        height: 'auto',
        paddingTop: 18,
    },

    label: {
        position: 'absolute',
        pointerEvents: 'none',
        fontSize: theme.fontSizes.xs,
        paddingLeft: theme.spacing.sm,
        paddingTop: theme.spacing.sm / 2,
        zIndex: 1,
    },
}));

export default function ChartSelectionForm({ onSubmit }) {
    const [chartType, setChartType] = useState('');
    const { classes } = useStyles();
    const { width, height } = useWindowSize();

    console.log(chartType)

    const handleChange = (event) => {
        console.log("here");
        if(event.target){
            setChartType(event.target.value);
        }
    };


    return (
        <>
            <Group position="center" spacing="xs" grow>

                <Select
                    style={{  zIndex: 2,  width: width-1100, marginTop: 0, marginBottom: 0}}
                    placeholder="Pick one"
                    label="Pick a chart type"
                    classNames={classes}
                    data={[
                        { value: 'bar', label: 'Bar' },
                        { value: 'pie', label: 'Pie' },
                    ]}
                    onChange={setChartType}
                />
                <Button style={{ zIndex: 2, height: 58, marginTop: 0, marginBottom: 0, marginRight: 0, marginLeft: 0}}
                    classNames={classes}
                    onClick={(event) => onSubmit(event, chartType)}>
                        Submit
                </Button>
            </Group>
        </>
    );
}