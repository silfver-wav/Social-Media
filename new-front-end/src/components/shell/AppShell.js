import { useState } from 'react';
import {
    AppShell,
    Navbar,
    Header,
    Footer,
    Aside,
    Text,
    MediaQuery,
    Burger,
    useMantineTheme,
} from '@mantine/core';
import {NavbarSimpleColored} from "../navComponents/Navbar";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import {CardsCarousel} from "../home/WelcomePage";
import {AuthenticationTitle} from "../autComponents/LoginPage";
import DashBoard from "../dashboard/DashBoard";
import UsersContainer from "../userComponents/users/UsersContainer";
import {ProfileContainer} from "../userComponents/profile/ProfileContainer";
import ChartContainer from "../chart/ChartContainer";
import FollowingContainer from "../userComponents/following/FollowingContainer";
import ChartCon from "../chart/ChartCon";
import Chat from "../chat/Chat";
import createChat from "../chat/sidebar/CreateChat";
import CreateChat from "../chat/sidebar/CreateChat";

export default function Shell({ handleLogout }) {
    const theme = useMantineTheme();
    const [opened, setOpened] = useState(false);
    return (
        <Router>
            <AppShell
                styles={{
                    main: {
                        background: theme.colorScheme === 'dark' ? theme.colors.dark[8] : theme.colors.gray[0],
                    },
                }}
                navbarOffsetBreakpoint="sm"
                asideOffsetBreakpoint="sm"
                navbar={
                    <Navbar p="md" hiddenBreakpoint="sm" hidden={!opened} width={{ sm: 200, lg: 300 }}>
                        <NavbarSimpleColored handleLogout={handleLogout}/>
                    </Navbar>
                }
                aside={
                    <MediaQuery smallerThan="sm" styles={{ display: 'none' }}>
                        <Aside p="md" hiddenBreakpoint="sm" width={{ sm: 200, lg: 300 }}>
                            <CreateChat />
                        </Aside>
                    </MediaQuery>
                }
                >
                <Routes>
                    <Route path="/home" element={<CardsCarousel />} />
                    <Route path="/following" element={<FollowingContainer/>} />
                    <Route path="/users" element={<UsersContainer/>} />
                    <Route path="/login" element={<AuthenticationTitle />} />
                    <Route path="/dashboard" element={<DashBoard />} />
                    <Route path="/profile" element={<ProfileContainer />} />
                    <Route path="/chart" element={<ChartCon />} />
                    <Route path="/chat" element={<Chat/>} />
                </Routes>
            </AppShell>
        </Router>
    );
}