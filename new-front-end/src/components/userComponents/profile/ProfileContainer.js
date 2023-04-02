import ProfilePage from "./ProfilePage";
import {Stack, Button, Collapse, Loader} from "@mantine/core";
import PostContainer from "../../posts/PostsContainerUser";
import {useEffect, useState} from "react";
import axios from "axios";
import {Center} from "@chakra-ui/react";
import { useWindowSize } from 'react-use';
import PostContainerUser from "../../posts/PostsContainerUser";

export function ProfileContainer() {
    const username = localStorage.getItem("username");
    const [opened, setOpened] = useState(false);
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

        axios.get(`http://localhost:8083/user/${username}`, requestOptions )
            .then((res) => {
                console.log(res.data);
                setUserData({...res.data.data});
            })
            .catch((err) => {
                console.log(err.response);
            });
    }, [username]);

    return (
        <Stack sx={(theme) => ({ backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[8] : theme.colors.gray[0], height: 550 })}>
            {userData ?
                <>
                    <ProfilePage data={userData} variant="outline"/>
                    {userData && (
                        <>
                            <Button onClick={() => setOpened((o) => !o)}>
                                Show Logs
                            </Button>
                            <Collapse in={opened}>
                                <PostContainerUser user={username}/>
                            </Collapse>
                        </>
                    )}
                </>
                :
                <Center height={height/2}>
                    <Loader color="teal" size="xl" variant="bars"/>
                </Center>
            }
        </Stack>
    );

}