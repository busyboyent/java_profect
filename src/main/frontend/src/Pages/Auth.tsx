import React, {Component} from 'react';
import './Auth.css'
import {Link} from "react-router-dom";
import {signup, SignInRequest, SignUpRequest, signin} from "../Auth/auth-api"
import auth from "../Auth/auth"

enum FormType {
    LOGIN,
    REGISTER
}

class Auth extends Component<any, any> {
    constructor(props: any) {
        super(props);
        this.state = {
            email: "",
            username: "",
            password: "",
            message: "",
            formType: FormType.LOGIN,
        };
        this.getForm = this.getForm.bind(this);
        this.changePage = this.changePage.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    getForm(formType: FormType): JSX.Element {
        if (formType === FormType.LOGIN) {
            return (
                <form onSubmit={this.handleSubmit(FormType.LOGIN)}>
                    <input type="text" value={this.state.email} onChange={this.handleChange("email")}
                           placeholder="Почта" className="auth-container-item"/>
                    <input type="password" value={this.state.password} onChange={this.handleChange("password")}
                           placeholder="Пароль" className="auth-container-item"/>
                    <label className="form-label"><input type="checkbox"/> Оставаться в системе </label> <br/>
                    <Link to="" className="form-label">Забыли пароль?</Link> <br/>
                    <input type="submit" className="blueButton auth-container-item" value="Войти"/>
                </form>
            );
        }
        return (
            <form onSubmit={this.handleSubmit(FormType.REGISTER)}>
                <input type="text" value={this.state.email} onChange={this.handleChange("email")} placeholder="Почта"
                       className="auth-container-item"/>
                <input type="text" value={this.state.username} onChange={this.handleChange("username")}
                       placeholder="Имя пользователя" className="auth-container-item"/>
                <input type="password" value={this.state.password} onChange={this.handleChange("password")}
                       placeholder="Пароль" className="auth-container-item"/>
                <label className="form-label"><input type="checkbox"/> Принимаю правила использования </label> <br/>
                <input type="submit" className="blueButton auth-container-item" value="Регистрация"/>
            </form>);
    }

    changePage(formType: FormType) {
        this.setState({formType: formType});
        console.log()
    }

    handleChange(field: string) {
        return (event: any) => {
            this.setState({[field]: event.target.value});
        };
    }

    handleSubmit(formType: FormType) {
        if (formType === FormType.REGISTER)
            return (event: any) => {
                signup(new SignUpRequest(this.state.email, this.state.username, this.state.password))
                    .then((result) => {
                        if(result["status"] === "ok"){
                            this.signin();
                            this.props.history.push('/user')
                        }
                        else{
                            alert(JSON.stringify(result));
                        }
                    });
                event.preventDefault();
            };
        return (event: any) => {
            this.signin()
            this.props.history.push('/user')
            event.preventDefault();
        }
    }

    signin(){
        signin(new SignInRequest(this.state.email, this.state.password))
            .then((result) => {
                auth.authenticate(result, ()=>{ this.props.history.push('/user') });
            })
    }

    private getActiveClassName = (formType: FormType) => {
        return this.state.formType === formType ? "active" : "";
    };

    render() {
        return (
            <React.Fragment>
                <div className="auth-container">
                    <div className="logo">Еда</div>
                    <div className="nav-panel auth-container-item">
                        <div className={"nav-panel-item " + this.getActiveClassName(FormType.LOGIN)} onClick={() => {
                            this.changePage(FormType.LOGIN)
                        }}>Войти
                        </div>
                        <div className={"nav-panel-item " + this.getActiveClassName(FormType.REGISTER)} onClick={() => {
                            this.changePage(FormType.REGISTER)
                        }}>
                            Регистрация
                        </div>
                    </div>
                    {this.getForm(this.state.formType)}
                </div>
            </React.Fragment>
        );
    }
}

export default Auth;
