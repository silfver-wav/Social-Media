import {createStyles, Text, Avatar, Group, TypographyStylesProvider, Paper, Image} from '@mantine/core';
import ChartRender from "../chart/ChartRender";

const useStyles = createStyles((theme) => ({
    comment: {
        padding: `${theme.spacing.lg}px ${theme.spacing.xl}px`,
    },

    text: {
        paddingLeft: 54,
        paddingTop: theme.spacing.sm,
        fontSize: theme.fontSizes.sm,

    },

    content: {
        '& > p:last-child': {
            marginBottom: 0,
        },
    },
}));


export function CommentHtml({ postedAt, image, chart, text, user }) {
    const { classes } = useStyles();

    //style={{ width: '100%', height: '100%', paddingBottom:50}}

    return (
        <Paper withBorder className={classes.comment} variant="outline" >
            <Group>
                <Avatar src={user.image} alt={user.name} radius="xl" />
                <div>
                    <Text size="sm">{user.name}</Text>
                    <Text size="xs" color="dimmed">
                        {postedAt}
                    </Text>
                </div>
            </Group>
            {chart && (
                <Paper style={{width: '100%', height: '500px', overflow: 'hidden'}} shadow="sm" p="md">
                    <ChartRender data={chart.data}
                                 chartType={chart.chartType}
                                 log={false}

                    ></ChartRender>
                </Paper>
            )}

            {image && (
                <Paper style={{width: '100%', overflow: 'hidden'}} shadow="sm" p="md">
                    <Image
                        size="sm"
                        radius="md"

                        src={`data:image/jpeg;base64,${image}`} alt="Personal Log"
                    ></Image>
                </Paper>
            )}


            <TypographyStylesProvider className={classes.text}>
                <div className={classes.content} dangerouslySetInnerHTML={{ __html: text }} />
            </TypographyStylesProvider>
        </Paper>
    );
}