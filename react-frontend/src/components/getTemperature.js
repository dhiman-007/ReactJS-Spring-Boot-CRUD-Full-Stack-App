

import Axios from 'axios';
import React, { Component } from 'react';

class getCityTemperature extends Component {

    constructor(props) {
        super(props)
        this.state = {
            responseObject: {},
            errorMessage: "",
            successful: false,
            city: ""
        }
    }

    getCityTemperature() {

        if (this.state.city.length === 0) {
            this.setState({ errorMessage: "Please Provie Valid City, It can't be Empty" })
            return;
        }
        const Weather_API_BASE_URL = "http://localhost:8081/api/v1/getWeather?cityName=" + this.state.city;
        return Axios.get(Weather_API_BASE_URL).then(Response => {
            const response = {
                cityName: Response.data.location.name,
                region: Response.data.location.region,
                country: Response.data.location.country,
                temperature: Response.data.current.temperature
            }
            this.setState({ 
                responseObject: response, 
                successful: true, 
                errorMessage: '' 
            })
        })
            .catch(error => {
                this.setState({ errorMessage: "Unable to Process Request with this Input", successful: false })
            })
    }

    inputText(e) {
        this.setState({
            city: e.target.value
        })
    }

    render(props) {
        return (
            
            <React.Fragment>
                <div align="center" style={{ backgroundColor: "grey" }}>

                    <input type="text" style={{ marginTop: "100px" }} onChange={(e) => this.inputText(e)} placeholder="Enter City..."></input>

                    <button className="btn btn-info" style={{ marginLeft: "-14%", marginTop: "120px" }} onClick={() => this.getCityTemperature()}> getTemperature</button>
                </div>

                {
                    this.state.errorMessage.length === 0 && this.state.successful
                        ?
                        <div style={{ marginTop: "150px" }}>
                            <h3 align="center">
                                City : {this.state.responseObject.cityName} <br />
                                Region : {this.state.responseObject.region} <br />
                                country : {this.state.responseObject.country} <br />
                                Temperature : {this.state.responseObject.temperature}
                            </h3>
                        </div>
                        :
                        <h3 style={{ marginTop: "150px", align: "center", color: "red" }}>{this.state.errorMessage}</h3>
                }
            </React.Fragment>
        );
    }
}

export default getCityTemperature;