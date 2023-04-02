import {Stack, Button, Loader} from '@mantine/core';
import {UserCardImage} from "./Users";
import {useEffect, useState} from "react";
import axios from "axios";
import {Center} from "@chakra-ui/react";
import { useWindowSize } from 'react-use';



export default function UsersContainer () {
    const username = localStorage.getItem("username");
    const [userData, setUserData] = useState(null);
    const { width, height } = useWindowSize();

    useEffect(() => {
        const requestOptions = {
            withCredentials: true,
            data: { },
            headers : {
                'Content-Type': 'application/json'
            }
        };

        axios.get(`http://localhost:8083/user/notfollowing/${username}`, requestOptions)
            .then((res) => {
                setUserData(res.data.data);
                console.log("userData " + userData);
            })
            .catch((err) => {
                console.log(err.response);
            });

    }, [])

    return (
        <Stack spacing="xs" sx={(theme) => ({ backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[8] : theme.colors.gray[0], height: '100%'})}>
            {userData ? userData.map((user, index) => (
                <UserCardImage
                    key={index}
                    initials={user.initials}
                    name={user.name}
                    username={user.username}
                    stats={user.stats}
                />
            )) :
                <Center height={height/2}>
                    <Loader color="teal" size="xl" variant="bars"/>
                </Center>
            }
        </Stack>
    );
}
