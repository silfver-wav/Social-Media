import {
    TextInput,
    PasswordInput,
    Paper,
    Title,
    Container,
    Button, Alert,
} from '@mantine/core';
import { Anchor } from '@mantine/core';
import {IconAlertCircle} from "@tabler/icons";
import {useState} from "react";

const API_URL = "http://localhost:8083/user/register";

export function RegisterTitle({setCreateNewAccount}) {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);

    const handleCreateAccount = async (e) => {
        e.preventDefault();

        // Validate form
        if (!firstName || !lastName || !email || !username || !password) {
            alert("Please enter all required fields.");
            return;
        }

        try {
            const response = await fetch(API_URL, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    firstName: firstName,
                    lastName: lastName,
                    email: email,
                    username: username,
                    password: password,
                }),
            });

            if (!response.ok) {
                throw new Error(response.message);
            }

            setCreateNewAccount(false);
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <Container size={420} my={40}>
            {error != null &&
                <Alert withCloseButton closeButtonLabel="Close alert"
                       icon={<IconAlertCircle size={16} />} title="Failed to create account!" color="red" onClick={async (e) => setError(null)}>
                    {error}
                </Alert>}

            <Title
                align="center"
                sx={(theme) => ({ fontFamily: `Greycliff CF, ${theme.fontFamily}`, fontWeight: 900 })}
            >
                Create your account
            </Title>

            <Paper withBorder shadow="md" p={30} mt={30} radius="md">
                <TextInput label="Email" placeholder="you@mantine.dev" require value={email} onChange={(e) => setEmail(e.target.value)}/>
                <TextInput label="Username" placeholder="Username" required value={username} onChange={(e) => setUsername(e.target.value)}/>
                <TextInput label="First Name" placeholder="Your first name" required value={firstName} onChange={(e) => setFirstName(e.target.value)}/>
                <TextInput label="Last Name" placeholder="Your last name" required value={lastName} onChange={(e) => setLastName(e.target.value)}/>
                <PasswordInput label="Password" placeholder="Your password" required mt="md" value={password} onChange={(e) => setPassword(e.target.value)}/>
                <Button fullWidth mt="xl" onClick={handleCreateAccount}>
                    Join
                </Button>
                <Anchor size="sm" onClick={handleCreateAccount} > Go back</Anchor>
            </Paper>
        </Container>
    );
}