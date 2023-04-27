import React, {Component} from 'react';
import '../Pages/Home.css'
import {Link,withRouter} from "react-router-dom";
import {addAddress, updateUserInfo} from "../API/api";

// eslint-disable-next-line @typescript-eslint/no-unused-vars
interface EditUserProfileWindowProps {
    closeButtonCallback: () => void;
    reloadProfileCallback: () => void;
    userProfile: any;
}

class EditUserProfileWindow extends Component<any, any> {
    constructor(props: any) {
        super(props);
        this.state = {
            username: this.props.userProfile["username"],
            email: this.props.userProfile["email"],
            newPassword: "",
            city: "", country: "", locationAddress: "", postalCode: ""
        }
    }

    componentDidMount(): void {

    }

    handleChange(field: string) {
        return (event: any) => {
            this.setState({[field]: event.target.value});
        };
    }

    handleSubmitUserinfo() {
        return (event: any) => {
            updateUserInfo(this.state.email, this.state.username, this.state.newPassword)
                .then((result) => {
                    if (result["status"] === "ok") {
                        alert(result["message"])
                        this.props.closeButtonCallback();
                        if(this.state.email !== this.props.userProfile.email){
                            alert("Please reauth after email change")
                            this.props.history.push("/auth");
                        }
                        this.props.reloadProfileCallback();
                    } else {
                        alert(JSON.stringify(result));
                    }
                });
            event.preventDefault();
        };
    }

    handleSubmitNewAddress() {
        return (event: any) => {
            addAddress(this.state.country, this.state.city, this.state.locationAddress, this.state.postalCode)
                .then((result) => {
                    if (result["status"] === "ok") {
                        this.props.reloadProfileCallback()
                        this.props.closeButtonCallback();
                        alert(result["message"])
                    } else {
                        alert(JSON.stringify(result));
                    }
                });
            event.preventDefault();
        };
    }

    render() {
        return (
            <React.Fragment>
                <div className="modal">
                    <div className="modal-content">
                        <Link to="/user" onClick={() => this.props.closeButtonCallback()} title="Close"
                              className="close">×</Link>
                        <div className="modal-body">
                            <h4 className="modal-title">Изменить личную информацию</h4>
                            <form onSubmit={this.handleSubmitUserinfo()}>
                                <input type="text" onChange={this.handleChange("email")} value={this.state["email"]}
                                       placeholder="Почта" className="auth-container-item"/>
                                <input type="text" onChange={this.handleChange("username")}
                                       value={this.state["username"]} placeholder="Имя пользователя"
                                       className="auth-container-item"/>
                                <input type="text" onChange={this.handleChange("newPassword")}
                                       value={this.state["newPassword"]}
                                       placeholder="Новый пароль (оставьте пустым, чтобы не менять)"
                                       className="auth-container-item"/> <br/> <br/>
                                <input type="submit" className="blueButton" value={"Обновить"}/>
                            </form>
                            <br/><br/>
                            <h4 className="modal-title">Добавить новый адрес</h4>
                            <form onSubmit={this.handleSubmitNewAddress()}>
                                <input type="text" onChange={this.handleChange("country")} value={this.state["country"]}
                                       placeholder="Страна" className="auth-container-item"/>
                                <input type="text" onChange={this.handleChange("city")} value={this.state["city"]}
                                       placeholder="Город" className="auth-container-item"/>
                                <input type="text" onChange={this.handleChange("locationAddress")}
                                       value={this.state["locationAddress"]} placeholder="Улица"
                                       className="auth-container-item"/>
                                <input type="text" onChange={this.handleChange("postalCode")}
                                       value={this.state["postalCode"]} placeholder="Почтовый индекс"
                                       className="auth-container-item"/> <br/> <br/>
                                <input type="submit" className="blueButton" value={"Добавить"}/>
                            </form>
                        </div>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default withRouter(EditUserProfileWindow);
