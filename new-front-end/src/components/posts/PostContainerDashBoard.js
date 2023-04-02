import {CommentHtml} from "./Post";
import { Stack, Button } from '@mantine/core';
import axios from "axios";
import {useEffect, useState} from "react";

export default function PostContainerDashBoard ({user}) {
    const [posts, setPosts] = useState([]);

    useEffect(() => {
        const requestOptions = {
            withCredentials: true,
            data: { },
            headers : {
                'Content-Type': 'application/json'
            }
        };

        axios.get(`http://localhost:8082/personal_log/getFollowingPosts/${user}`, requestOptions )
            .then((res) => {
                console.log(res.data);
                setPosts(res.data.data);
            })
            .catch((err) => {
                console.log(err.response);
            });
    }, [user]);

    return (
        <Stack spacing="xs" sx={(theme) => ({ backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[8] : theme.colors.gray[0], height: 300})}>

            {posts &&
                posts.map((post) => (
                    <CommentHtml
                        key={post.id}
                        postedAt={post.postedAt}
                        image={post.image}
                        chart={post.chart}
                        text={post.text}
                        user={post.username}
                    />
                ))
            }
        </Stack>
    );
}
