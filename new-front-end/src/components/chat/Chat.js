import * as React from "react";
import {
    Avatar,
    FileButton,
    TextInput
} from "@mantine/core";
import './Chat.css';
import {IconChartBar, IconPhotoUp} from "@tabler/icons";
import {useEffect, useRef, useState} from "react";
import SockJS from "sockjs-client";
import Stomp from 'stompjs';
import {useLocation} from "react-use";
import axios from "axios";

const mockMessages = [
        {
            sender: 'ricci',
            receiver: 'user',
            text: 'Hello, how are you?',
        },
        {
            sender: 'user',
            receiver: 'ricci',
            text: 'I am fine, thank you!',
        },
        {
            sender: 'ricci',
            receiver: 'user',
            text: 'What about you?',
        },
        {
            sender: 'ricci',
            receiver: 'user',
            text: 'What about you?',
        },
        {
            sender: 'ricci',
            receiver: 'user',
            text: 'What about you?',
        }
    ];

const sender = localStorage.getItem("sender");
const receiver = localStorage.getItem("receiver");
export default function Chat() {

const [messageValue, setMessageValue] = useState('');
const dummy = useRef();
const [messages, setMessages] = useState([]);
const [file, setFile] = useState(null);

    let socket = new SockJS('http://localhost:8081/chat');
    let stompClient = Stomp.over(socket);

    useEffect(() => {
        stompClient.connect({}, onConnected, onError)

        const requestOptions = {
            withCredentials: true,
            data: { },
            headers : {
                'Content-Type': 'application/json'
            }
        };

        axios.get(`http://localhost:8081/message/${sender}/${receiver}`, requestOptions)
            .then((res) => {
                console.log(res.data);
                //messages.push(res.data.data);
                setMessages([...res.data.data]);
            })
            .catch((err) => {
                console.log(err.response);
            });


        return () => {
            disconnect();
        }

    }, [])

    let isConnected = false;

    const onConnected = () => {
        isConnected = true;
        stompClient.subscribe('/user/'+sender+'/private', onMessageReceived);
    }

    const onError = (err) => {
        console.log(err);
    }

    const disconnect = () => {
        //stompClient.disconnect();
    }

    const onMessageReceived = (payload) => {
        const payloadData = JSON.parse(payload.body);
        if (payloadData) {
            console.log("message: ")
            console.log(payloadData);
            messages.push(payloadData);
            setMessages([...messages]);
        }
    }

    const sendMessage = async (e) => {
        e.preventDefault();
        if (stompClient) {
            console.log("here");
            const chatMessage = {
                sender: sender,
                receiver: receiver,
                text: messageValue,
                image: file
            };
            console.log("sender: ", sender, "receiver: ", receiver, "text: ", messageValue, "image: ", file);
            stompClient.send('/chat/private-message', {}, JSON.stringify(chatMessage));

            messages.push(chatMessage);
            setMessages([...messages]);
            setMessageValue('');
        }
        dummy.current.scrollIntoView({behavior: 'smooth'});
    }

    const handleFileUpload = event => {
        const file = event.target.files[0];
        const reader = new FileReader();
        reader.onload = () => {
            const base64Image = reader.result;
            setFile(base64Image);
        };
        reader.readAsDataURL(file);
    };



    return (
    <>
            <main>
                {messages.map(msg => <Message key={msg.id} message={msg}/>)}
            </main>


        <form onSubmit={sendMessage}>

            {/*
            <FileButton variant="subtle" style={{width: "10vh"}} onChange={handleFileUpload} accept="image/png,image/jpeg">
                <IconPhotoUp type="mark" size={25} />
            </FileButton>
            */}
            <TextInput
                style={{width: "100%", backgroundColor:"black", color:"white"}}
                placeholder="Aa"
                value={messageValue}
                onChange={(e) => setMessageValue(e.target.value)}
            />

        </form>
    </>
    )

}

function Message(props) {
    const msg = props.message;
    const messageClass = msg.sender === sender ? 'sent' : 'received';

    return (
        <>
            <div className={`message ${messageClass}`} variant="outline">
                <Avatar radius="xl" style={{color:"cyan", backgroundColor:"black", position: "relative"}}>{sender.charAt(0)}</Avatar>
                <p className="message-text" style={{color:"white", backgroundColor:"black"}}>{msg.text}</p>
            </div>
        </>
    );
}