import {createStyles, Avatar, Badge, Table, Group, Text, Paper, ScrollArea, Button, Flex} from '@mantine/core';
import { useNavigate } from 'react-router-dom';
import Chat from "../../chat/Chat";

const rolesData = ['Manager', 'Collaborator', 'Contractor'];

const useStyles = createStyles(theme => ({
    card: {
        backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[7] : theme.white,
    },

}));

export function UsersRolesTable({data}) {
    const currentUser = localStorage.getItem('username');
    const { classes, theme } = useStyles();
    const navigate = useNavigate();

    const handleChatClick = (username) => {
        console.log("current user: " + currentUser + " receiver: " + username);
        //navigate('/chat', { state: { sender: currentUser, receiver: username } });
        localStorage.setItem("sender", currentUser);
        localStorage.setItem("receiver", username)
        navigate('/chat');

    };
    const rows = data.map((item) => (
        <div key={item.username}>
            <Flex
                mih={50}
                gap="xs"
                justify="flex-start"
                align="center"
                direction="row"
                wrap="wrap"
            >
                <Group spacing="sm">
                    <Avatar color="cyan" radius="xl">{item.initials}</Avatar>
                    <div>
                        <Text size="sm" weight={500}>
                            {item.username}
                        </Text>
                        <Text size="xs" color="dimmed">
                            {item.name}
                        </Text>
                    </div>
                </Group>

            <Button style={{marginLeft:'auto'}} color={theme.colorScheme === 'dark' ? undefined : 'dark'} radius="xl"
                    onClick={() => handleChatClick(item.username)}
            >
                Send Message
            </Button>
            </Flex>
        </div>
    ));

    return (
        <ScrollArea>
            <Table  verticalSpacing="sm">
                <Paper shadow="xs" p="md" radius="xl"> {rows} </Paper>
            </Table>
        </ScrollArea>
    );
}