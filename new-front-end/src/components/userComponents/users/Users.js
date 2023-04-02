import React from 'react';
import { createStyles, Card, Avatar, Text, Group, Button } from '@mantine/core';
import axios from "axios";

const useStyles = createStyles(theme => ({
    card: {
        backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[7] : theme.white,
        //height: "20vh",
        minHeight: "300px"
    },
    avatar: {
        border: `2px solid ${theme.colorScheme === 'dark' ? theme.colors.dark[7] : theme.white}`,
        backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[7] : theme.white,
    },
}));


export const UserCardImage = ({ initials, name, username, stats }) => {
    const { classes, theme } = useStyles();
    const [follow, setFollow] = React.useState('Follow');

    const handleFollow = async (e) => {
        e.preventDefault();


        const formData = new FormData();
        formData.append('personalLog', JSON.stringify({
            followerUsername: localStorage.getItem('username'),
            followingUsername: username,
        }));

        const response = await fetch("http://localhost:8083/user/follow", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                followerUsername: localStorage.getItem("username"),
                followingUsername: username
            }),
        })

        if (!response.ok) {
            throw new Error(response.message);
        } else {
            setFollow('Following');
        }
    };

    const items = stats.map(stat => (
        <div key={stat.label}>
            <Text align="center" size="lg" weight={400} height={10}>
                {stat.value}
            </Text>
            <Text align="center" size="sm" color="dimmed">
                {stat.label}
            </Text>
        </div>
    ));

    return (
        <Card withBorder p="md" radius="md" className={classes.card} variant="outline" spacing={5}>
            <Card.Section sx={{ backgroundImage: `url(https://images.unsplash.com/photo-1502252430442-aac78f397426?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2370&q=80)`, minHeight: "101px", backgroundSize: 'cover'  }} />
            <Avatar color="cyan" radius="md" size={50} radius={50} mx="auto" mt={-30} className={classes.avatar}>
                {initials}
            </Avatar>
            <Text align="center" size="sm" weight={500} mt="sm">
                {username}
            </Text>
            <Text align="center" size="xs" color="dimmed">
                {name}
            </Text>
            <Group mt="sm" position="center" spacing={20}>
                {items}
                {follow === 'Follow' ?
                    <Button onClick={handleFollow} fullWidth radius="md" size="sm" color={theme.colorScheme === 'dark' ? undefined : 'dark'}>Follow</Button> :
                    <Button fullWidth radius="md" size="sm" color={theme.colorScheme === 'dark' ? undefined : 'dark'}>Following</Button>
                }
            </Group>
        </Card>
    );
};
