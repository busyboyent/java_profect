import auth from "./auth";
import {getApiPrefix} from "../API/constants";

class SignInRequest{
    email: String;
    password: String
    constructor(email: String, password: String) {
        this.email = email;
        this.password = password;
    }
}

class SignUpRequest{
    email: String;
    username: String;
    password: String;
    constructor(email: String, username: String, password: String) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}

const signin = async (user: SignInRequest) => {
    try {
        let response = await fetch( `${getApiPrefix()}auth/signin`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            credentials: 'include',
            body: JSON.stringify(user)
        })
        return await response.json()
    } catch(err) {
        console.log(err)
        return err
    }
}

const signup = async (user: SignUpRequest) => {
    try{
        let response = await fetch(`${getApiPrefix()}auth/signup`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            credentials: 'include',
            body: JSON.stringify(user)
        })
        return await response.json()
    }
    catch(err){
        console.log(err)
        return err
    }
}

const getProfile = async () => {
    try{
        let response = await fetch(`${getApiPrefix()}auth/profile`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + auth.isAuthenticated()["accessToken"],
            },
            credentials: 'include',
        })
        return await response.json()
    }
    catch(err){
        console.log(err)
        throw err
    }
}

const signout = async () => {
    try {
        let response = await fetch(`${getApiPrefix()}/auth/signout`, { method: 'GET' })
        return await response.json()
    } catch(err) {
        console.log(err)
        return err
    }
}

export {
    signin,
    signup,
    signout,
    getProfile,
    SignInRequest,
    SignUpRequest
}