import {Stack, Button, Textarea, Paper, Divider, Box, Group, FileButton, Flex, Alert} from '@mantine/core';
import sendIcon from '../../assets/tree.png';
import {useState} from "react";
import {IconAlertCircle, IconChartBar, IconPhotoUp} from "@tabler/icons";
import axios from "axios";
import PostContainerDashBoard from "../posts/PostContainerDashBoard";

const API_URL = "http://localhost:8082/personal_log/add";

export default function DashBoard () {
    const [text, setText] = useState('');
    const username = localStorage.getItem("username");
    const [file, setFile] = useState(null);
    const [error, setError] = useState(null);

    const handlePost = async (e) => {
        e.preventDefault();

        if (text === '' && file === null) {
            setError("You cannot post an empty message.");
            return;
        }

        if (file) {
            console.log("here")
            const base64EncodedImage = await handleFileUpload();
            const postData = {
                text: text,
                username: username,
                image: base64EncodedImage
            };
            console.log("here")
            await postToApi(postData);
        } else {
            const postData = {
                text: text,
                username: username
            };
            await postToApi(postData);
        }
    };

    const handleFileUpload = () => {
        console.log("here")
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => {
                const base64EncodedImage = reader.result?.split(",")?.[1];
                resolve(base64EncodedImage);
            };
            reader.onerror = reject;
        });
    };

    const postToApi = async (postData) => {
        try {
            const response = await axios.post(API_URL, postData, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            console.log(response);
        } catch (error) {
            console.log(error);
        }
    };

     return (
         <>
             {error != null &&
                 <Alert withCloseButton closeButtonLabel="Close alert"
                        icon={<IconAlertCircle size={16} />} title="Post failed!" color="red" onClick={async (e) => setError(null)}>
                     You cannot post an empty message!
                 </Alert>}

             <Stack sx={(theme) => ({ backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[8] : theme.colors.gray[0], height: 300 })}>
                 <Paper shadow="xs" p="md">
                     <Textarea
                         variant="outline"
                         placeholder="What's happening?"
                         autosize
                         value={text}
                         onChange={(event) => setText(event.currentTarget.value)}
                     />
                     <Divider my="sm" />
                     <Flex
                         mih={50}
                         gap="xs"
                         justify="flex-start"
                         align="center"
                         direction="row"
                         wrap="wrap"
                     >
                         <FileButton onChange={setFile} accept="image/png,image/jpeg">
                             {(props) => <Button {...props} variant="subtle" >
                                 <IconPhotoUp type="mark" size={23} />
                             </Button>}
                         </FileButton>

                         <Button variant="subtle" >
                             <IconChartBar type="mark" size={23} />
                         </Button>

                         <Button
                             style={{marginLeft: "auto"}}
                             onClick={handlePost}>
                             Post
                             <img style={{paddingLeft:5}} src={sendIcon} width="20" height="20"/>
                         </Button>
                     </Flex>
                 </Paper>
                 <Divider my="sm" />
                 <Box variant="outline">
                     <PostContainerDashBoard user={username}/>
                 </Box>
             </Stack>

         </>

     );
 }