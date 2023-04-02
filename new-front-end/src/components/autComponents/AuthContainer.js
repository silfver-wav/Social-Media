import Shell from "../shell/AppShell";
import React, {useState} from "react";
import {RegisterTitle} from "./RegisterPage";
import {AuthenticationTitle} from "./LoginPage";

export default function AuthContainer({setIsLoggedIn}) {
    const [createNewAccount, setCreateNewAccount] = useState(false);
    function handleRegister() {
        setCreateNewAccount(true);
    }

    return (
        <>
            {createNewAccount ?
                <RegisterTitle setCreateNewAccount={setCreateNewAccount}/> :
                <AuthenticationTitle setIsLoggedIn={setIsLoggedIn} handleRegister={handleRegister}/>}
        </>
    );
}