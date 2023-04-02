import {Button, MultiSelect} from "@mantine/core";
import {useEffect, useRef, useState} from "react";
import axios from "axios";
import {IconChartBar} from "@tabler/icons";

const data = [
    { value: 'react', label: 'React' },
    { value: 'ng', label: 'Angular' },
    { value: 'svelte', label: 'Svelte' },
    { value: 'vue', label: 'Vue' },
    { value: 'riot', label: 'Riot' },
    { value: 'next', label: 'Next.js' },
    { value: 'blitz', label: 'Blitz.js' },
];


export default function CreateChat() {
    const username = localStorage.getItem("username");
    const [users, setUsers] = useState(null);
    const [value, setValue] = useState([]);
    const [roomName, setRoomName] = useState("");
    const socket = useRef(new WebSocket(`ws://localhost:8083/chat`));

    useEffect(() => {
        const requestOptions = {
            withCredentials: true,
            data: { },
            headers : {
                'Content-Type': 'application/json'
            }
        };

        axios.get(`http://localhost:8083/user/getFollowing/${username}`, requestOptions)
            .then((res) => {
                console.log(res.data);
                setUsers(res.data.data);
            })
            .catch((err) => {
                console.log(err.response);
            });
    }, [])

    const handleClick = async (e) => {
        console.log("value: " + value);

        // POST request creating a new chat
    }

    return (
        <>
            {users ? (
                <MultiSelect
                    data={users.map(user => ({
                        value: user.username,
                        label: user.name
                    }))}
                    placeholder="Pick users to chat with"
                    searchable
                    nothingFound="Nothing found"
                    clearButtonLabel="Clear selection"
                    clearable
                    maxDropdownHeight={160}
                    transitionDuration={150}
                    transition="pop-top-left"
                    transitionTimingFunction="ease"
                    value={value}
                    onChange={setValue}
                />
            ) : (
                <div>Loading...</div>
            )}

            <Button variant="subtle" onClick={handleClick}>
                Create Chat
            </Button>
        </>
    )
}