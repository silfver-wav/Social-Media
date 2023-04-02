import {useEffect, useState} from "react";
import axios from "axios";
import {Loader, Stack} from "@mantine/core";
import {UserCardImage} from "../users/Users";
import {Center} from "@chakra-ui/react";
import {UsersRolesTable} from "./Following";

export default function FollowingContainer () {
    const username = localStorage.getItem("username");
    const [userData, setUserData] = useState(null);

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
                setUserData(res.data.data);
            })
            .catch((err) => {
                console.log(err.response);
            });
    }, [])


    return (
        <Stack spacing="xs" sx={(theme) => ({ backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[8] : theme.colors.gray[0], height: '100%'})}>
            {userData ?
                <UsersRolesTable data={userData}/> :
                <Center>
                    <p>You seem to not be following any one at the moment</p>
                </Center>
            }
        </Stack>
    );
}