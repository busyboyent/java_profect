import {signout} from './auth-api'

const auth = {
    isAuthenticated() {
        if (typeof window == "undefined")
            return false

        if (localStorage.getItem('jwt'))
            return JSON.parse(localStorage.getItem('jwt') as string)
        else
            return false
    },
    authenticate(jwt: any, callback: any) {
        if (typeof window !== "undefined") {
            if (jwt['accessToken']) {
                localStorage.setItem('jwt', JSON.stringify(jwt))
                callback('')
            } else {
                callback('Wrong e-mail or password')
            }
        }
    },
    clearJWT(callback: any) {
        if (typeof window !== "undefined")
            localStorage.removeItem('jwt')
        callback()
        signout().then((data) => {
            document.cookie = "t=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;"
        })
    },
    updateUser(user: any, callback: any) {
        if (typeof window !== "undefined") {
            if (localStorage.getItem('jwt')) {
                let auth = JSON.parse(localStorage.getItem('jwt') as string)
                auth.user = user
                localStorage.setItem('jwt', JSON.stringify(auth))
                callback()
            }
        }
    }
}

export default auth
