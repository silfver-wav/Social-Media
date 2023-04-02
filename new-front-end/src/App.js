import {MantineProvider} from '@mantine/core';
import Shell from "./components/shell/AppShell";
import React, {useEffect, useState} from "react";
import {AuthenticationTitle} from "./components/autComponents/LoginPage";
import AuthContainer from "./components/autComponents/AuthContainer";

const username = localStorage.getItem('username');
export default function App() {

    const [isLoggedIn, setIsLoggedIn] = useState(false);
    function handleLogout() {
        setIsLoggedIn(false);
    }

    useEffect(() => {
        if (username) {
            setIsLoggedIn(true);
        } else {
            setIsLoggedIn(false);
        }
    }, [username]);


    return (
        <MantineProvider withGlobalStyles withNormalizeCSS theme={{
            fontFamily: 'Roboto, sans-serif, Playfair Display',
            colorScheme: 'dark',
            fontSizes: { md: 16, lg: 18, xl: 20, xxl: 24 },
            colors: {
                brand: [ '#E7FEF7',
            '#BCFBE8',
            '#91F8D8',
            '#65F6C9',
            '#3AF3BA',
            '#0FF0AB',
            '#0CC089',
            '#099067',
            '#066044',
            '#033022'],
            },
            primaryColor: 'brand',
        }}>
            {isLoggedIn ?
                <Shell handleLogout={handleLogout}/> :
                <AuthContainer setIsLoggedIn={setIsLoggedIn}/>}
        </MantineProvider>
    );
}
