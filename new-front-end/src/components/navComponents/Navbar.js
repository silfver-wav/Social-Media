import {useContext, useState} from 'react';
import { createStyles, Navbar, Group, Code } from '@mantine/core';
import {
    IconHome2,
    IconUser,
    IconSettings,
    IconGauge,
    IconSwitchHorizontal,
    IconLogout,
    IconChartBar,
    IconUsers,
    IconFriends,
} from '@tabler/icons';
import forestIcon from '../../assets/forest.png';
import { useWindowSize } from 'react-use';

import {Link} from "react-router-dom";
import {Text} from "@chakra-ui/react";

const useStyles = createStyles((theme, _params, getRef) => {
    const icon = getRef('icon');
    return {
        navbar: {
            backgroundColor: theme.fn.variant({ variant: 'filled', color: theme.primaryColor }).background,
        },
        version: {
            backgroundColor: theme.fn.lighten(
                theme.fn.variant({ variant: 'filled', color: theme.primaryColor }).background,
                0.1
            ),
            color: theme.white,
            fontWeight: 700,
        },
        header: {
            paddingBottom: theme.spacing.md,
            marginBottom: theme.spacing.md * 1.5,
            borderBottom: `1px solid ${theme.fn.lighten( theme.fn.variant({ variant: 'filled', color: theme.primaryColor }).background, 0.1 )}`,
},
    footer: {
        paddingTop: theme.spacing.md,
            marginTop: theme.spacing.md,
            borderTop: `1px solid ${theme.fn.lighten(
                theme.fn.variant({ variant: 'filled', color: theme.primaryColor }).background,
                0.1
            )}`, }, link: { ...theme.fn.focusStyles(), display: 'flex', alignItems: 'center', textDecoration: 'none', fontSize: theme.fontSizes.sm, color: theme.white, padding: `${theme.spacing.xs}px ${theme.spacing.sm}px`,
        borderRadius: theme.radius.sm,
        fontWeight: 500,
        '&:hover': {
            backgroundColor: theme.fn.lighten(
                theme.fn.variant({ variant: 'filled', color: theme.primaryColor }).background,
                0.1
            ),
        },
    },
        linkIcon: {
            ref: icon,
            color: theme.white,
            opacity: 0.75,
            marginRight: theme.spacing.sm,
        },
        linkActive: {
            '&, &:hover': {
                backgroundColor: theme.fn.lighten(
                    theme.fn.variant({ variant: 'filled', color: theme.primaryColor }).background,
                    0.15
                ),opacity: 0.9,
            },
        },
    };
});

const data = [
    { link: '/home', label: 'Home', icon: IconHome2 },
    { link: '/dashboard', label: 'Dashboard', icon: IconGauge },
    { link: '/following', label: 'Following', icon: IconFriends },
    { link: '/users', label: 'Users', icon: IconUsers },
    { link: '/chart', label: 'Chart', icon: IconChartBar },
    { link: '/profile', label: 'Profile', icon: IconUser },
    { link: '', label: 'Settings', icon: IconSettings },
];

export function NavbarSimpleColored({ handleLogout }) {
    const { width, height } = useWindowSize();
    const { classes, cx } = useStyles();
    const [active, setActive] = useState('Billing');


    const links = data.map((item) => (
        <Link
            className={cx(classes.link, { [classes.linkActive]: item.label === active })}
            to={item.link}
            key={item.label}
            onClick={(event) => {
                setActive(item.label);
            }}
        >
            <item.icon className={classes.linkIcon} stroke={1.5} />
            <span>{item.label}</span>
        </Link>
    ));

    return (
        <Navbar height={height} width={{ sm: 300 }} p="md" className={classes.navbar}>
            <Navbar.Section grow>
                <Group className={classes.header} position="apart">
                    <img src={forestIcon} width="55" height="55" />
                </Group>
                {links}
            </Navbar.Section>

            <Navbar.Section className={classes.footer}>
                <Link to='/login' className={classes.link} onClick={(event) => {event.preventDefault(); handleLogout();}}>
                    <IconSwitchHorizontal className={classes.linkIcon} stroke={1.5} />
                    <span>Change account</span>
                </Link>
                <Link href="#" className={classes.link} onClick={(event) => {event.preventDefault(); handleLogout();}}>
                    <IconLogout className={classes.linkIcon} stroke={1.5} />
                    <span>Logout</span>
                </Link>
            </Navbar.Section>
        </Navbar>
    );
}