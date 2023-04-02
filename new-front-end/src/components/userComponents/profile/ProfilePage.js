import { createStyles, Card, Avatar, Text, Group, Button } from '@mantine/core';
import React from "react";
import { useWindowSize } from 'react-use';

const useStyles = createStyles(theme => ({
    card: {
        backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[7] : theme.white,
    },
    avatar: {
        border: `2px solid ${theme.colorScheme === 'dark' ? theme.colors.dark[7] : theme.white}`,
        backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[7] : theme.white,
    },
}));


export default function ProfilePage(props) {
    const { data } = props;
    const { width, height } = useWindowSize();
    const { classes, theme } = useStyles();

    const items = data.stats.map(stat => (
        <div key={stat.label}>
            <Text align="center" size="lg" weight={500}>
                {stat.value}
            </Text>
            <Text align="center" size="sm" color="dimmed">
                {stat.label}
            </Text>
        </div>
    ));



    return (
        <Card withBorder p="xl" height={300} radius="lg" className={classes.card} variant="outline">
            <Card.Section sx={{ backgroundImage: `url(https://images.unsplash.com/photo-1502252430442-aac78f397426?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2370&q=80)`, height: 250, backgroundSize: 'cover' }} />
            <Avatar color="cyan" radius="xl" size={100} radius={80} mx="auto" mt={-40} className={classes.avatar}>{data.initials}</Avatar>
            <Text align="center" size="xl" weight={500} mt="sm">
                {data.username}
            </Text>
            <Text align="center" size="md" color="dimmed">
                {data.name}
            </Text>
            <Text align="center" size="md" color="dimmed">
                {data.email}
            </Text>
            <Group mt="md" position="center" spacing={30}>
                {items}
            </Group>

            {/*}
            <Group mt="md" position="center" spacing={30}>
                <div key={followers}>
                    <Text align="center" size="lg" weight={500}>
                        Followers
                    </Text>
                    <Text align="center" size="sm" color="dimmed">
                        {followers}
                    </Text>
                </div>
                <div >
                    <Text align="center" size="lg" weight={500}>
                        {following}
                    </Text>
                    <Text align="center" size="sm" color="dimmed">
                        following
                    </Text>
                </div>
            </Group>
            */}

        </Card>
    );

};