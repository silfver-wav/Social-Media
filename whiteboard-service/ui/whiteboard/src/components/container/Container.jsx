import React from 'react';
import Board from '../board/Board';

import './Container.css';


class Container extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            color: "#000000",
            size: "5"
        }
    }

    changeColor(params) {
        this.setState({
            color: params.target.value
        })
    }

    changeSize(params) {
        this.setState({
            size: params.target.value
        })
    }
    
    render() {

        return (
            <div className="container">
                <div class="tools-section">
                    <div className="color-picker-container">
                        Select Brush Color : &nbsp;
                        <input type="color" value={this.state.color} onChange={this.changeColor.bind(this)}/>
                    </div>
                    <div className="brush-size-container">
                        Select Brush Size : &nbsp;
                        <input type="range" min="5" max="100" value={this.state.size} onChange={this.changeSize.bind(this)}/>
                    </div>
                </div>
                <div class="board-container">
                    <Board 
                        color={this.state.color} 
                        size={this.state.size}
                    ></Board>
                </div>
            </div>
        )
    }
}

export default Container