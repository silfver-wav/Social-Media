import {
    TextInput,
    PasswordInput,
    Checkbox,
    Anchor,
    Paper,
    Title,
    Text,
    Container,
    Group,
    Button, Alert,
} from '@mantine/core';
import {useState} from "react";
import {IconAlertCircle} from "@tabler/icons";

const API_URL = "http://localhost:8083/user/login";

export function AuthenticationTitle({setIsLoggedIn, handleRegister}) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);

    const handleSignIn = async (event) => {
        event.preventDefault();

        try {
            const response = await fetch(API_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username: username, password: password }),
            });

            if (!response.ok) {
                throw new Error('Unable to login. Please check your email and password and try again.');
            }

            const data = await response.json();

            // Store the token in local storage or a cookie
            //localStorage.setItem('token', username);
            localStorage.setItem("username", username);

            setIsLoggedIn(true);
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <Container size={420} my={40}>
            {error != null &&
                <Alert withCloseButton closeButtonLabel="Close alert"
                       icon={<IconAlertCircle size={16} />} title="Login Failed!" color="red" onClick={async (e) => setError(null)}>
                Wrong password or email!
            </Alert>}

            <Title
                align="center"
                sx={(theme) => ({ fontFamily: `Greycliff CF, ${theme.fontFamily}`, fontWeight: 900 })}
            >
                Welcome back!
            </Title>
            <Text color="dimmed" size="sm" align="center" mt={5}>
                Do not have an account yet?{' '}
                {/*<Anchor href="#" color="blue"> Create one</Anchor>*/}
                <Anchor size="sm" onClick={(event) => {event.preventDefault(); handleRegister();}}>
                Create account
            </Anchor>
            </Text>

            <Paper withBorder shadow="md" p={30} mt={30} radius="md">
                <TextInput label="Username" placeholder="you@mantine.dev" required value={username} onChange={(e) => setUsername(e.target.value)} />
                <PasswordInput label="Password" placeholder="Your password" required mt="md" value={password} onChange={(e) => setPassword(e.target.value)} />
                <Group position="apart" mt="lg">
                    <Checkbox label="Remember me" sx={{ lineHeight: 1 }} />
                </Group>
                <Button fullWidth mt="xl" onClick={handleSignIn}>
                    Sign in
                </Button>
            </Paper>
        </Container>
    );
}
