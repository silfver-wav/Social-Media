
import videoBackground from './videoBackground2.mp4';
import { useWindowSize } from 'react-use';


/*
export function CardsCarousel() {
    const { width, height } = useWindowSize();

    return (
        <div style={{ justifyContent: "center", height:height, alignItems: "center" }}>
            <video src={videoBackground} autoPlay loop muted style={{ width: "100%", height: "100%", objectFit: "cover"}}>
                <p>Welcome</p>
            </video>
        </div>
    );
}
*/

export function CardsCarousel() {
    const { width, height } = useWindowSize();

    return (
        <div style={{ position: "relative", width: "100%", height: height }}>
            <video
                src={videoBackground}
                autoPlay
                loop
                muted
                style={{ width: "100%", height: "100%", objectFit: "cover" }}
            >
                <p>Welcome</p>
            </video>
            <div
                style={{
                    position: "absolute",
                    top: "50%",
                    left: "50%",
                    transform: "translate(-50%, -50%)",
                    color: "white",
                    fontSize: "4em",
                    fontWeight: "bold",
                    textAlign: "center",
                    textShadow: "2px 2px 4px rgba(0, 0, 0, 0.5)",
                }}
            >
                Welcome
            </div>
        </div>
    );
}

