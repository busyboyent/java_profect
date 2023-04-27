import {getApiPrefix} from "./constants";
import auth from "../Auth/auth";

const getGoods = async (category?: string) => {
    const getUrl = (category?: string)=> {
        if(category != null) {
            return `${getApiPrefix()}goods?category=${category}`
        }
        else{
            return `${getApiPrefix()}goods`
        }
    }
    try {
        let response = await fetch(getUrl(category), {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            credentials: 'include',
        })
        return await response.json()
    } catch (err) {
        console.log(err)
        throw err
    }
}

const searchGoods = async (search: string) => {
    try {
        let response = await fetch(`${getApiPrefix()}goods?search=${search}`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            credentials: 'include',
        })
        return await response.json()
    } catch (err) {
        console.log(err)
        throw err
    }
}

const getProductInfo = async (productId: string) => {
    try {
        let response = await fetch(`${getApiPrefix()}goods?id=${productId}`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            credentials: 'include',
        })
        return await response.json()
    } catch (err) {
        console.log(err)
        throw err
    }
}

const addAddress = async (country: string, city: string, locationAddress: string, postalCode: string) => {
    try {
        let response = await fetch(`${getApiPrefix()}addresses/`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + auth.isAuthenticated()["accessToken"],
            },
            credentials: 'include',
            body: JSON.stringify(
                {
                    "addressEntity":
                        {
                            "country": country,
                            "city": city,
                            "locationAddress": locationAddress,
                            "postalCode": postalCode
                        }
                }
            )
        })
        return await response.json()
    } catch (err) {
        console.log(err)
        throw err
    }
}

const updateUserInfo = async (email: string, username: string, newPassword: string) => {
    try {
        let response = await fetch(`${getApiPrefix()}auth`, {
            method: 'PATCH',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + auth.isAuthenticated()["accessToken"],
            },
            credentials: 'include',
            body: JSON.stringify(
                {
                    "email": email,
                    "username": username,
                    "newPassword": newPassword
                }
            )
        })
        return await response.json()
    } catch (err) {
        console.log(err)
        throw err
    }
}

const removeAddress = async (id: number) => {
    try {
        let response = await fetch(`${getApiPrefix()}addresses/`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + auth.isAuthenticated()["accessToken"],
            },
            credentials: 'include',
            body: JSON.stringify({"addressId": id})
        })
        return await response.json()
    } catch (err) {
        console.log(err)
        throw err
    }
}

const getCategoryList = async () => {
    try {
        let response = await fetch(`${getApiPrefix()}goods/categories`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            credentials: 'include',
        })
        return await response.json()
    } catch (err) {
        console.log(err)
        throw err
    }
}

const getOrders = async () => {
    try {
        let response = await fetch(`${getApiPrefix()}orders`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + auth.isAuthenticated()["accessToken"],
            },
            credentials: 'include',
        })
        return await response.json()
    } catch (err) {
        console.log(err)
        throw err
    }
}

const cancelOrder = async () => {
    try {
        let response = await fetch(`${getApiPrefix()}orders`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + auth.isAuthenticated()["accessToken"],
            },
            credentials: 'include',
        })
        return await response.json()
    } catch (err) {
        console.log(err)
        throw err
    }
}


const getCart = async () => {
    try {
        let response = await fetch(`${getApiPrefix()}cart`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + auth.isAuthenticated()["accessToken"],
            },
            credentials: 'include',
        })
        return await response.json()
    } catch (err) {
        console.log(err)
        throw err
    }
}

const addToCart = async (Id: number) => {
    try {
        let response = await fetch(`${getApiPrefix()}cart`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + auth.isAuthenticated()["accessToken"],
            },
            credentials: 'include',
            body: `${Id}`
        })
        return await response.json()
    } catch (err) {
        console.log(err)
        throw err
    }
}

const deleteFromCart = async (Id: number) => {
    try {
        let response = await fetch(`${getApiPrefix()}cart`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + auth.isAuthenticated()["accessToken"],
            },
            credentials: 'include',
            body: `${Id}`
        })
        return await response.json()
    } catch (err) {
        console.log(err)
        throw err
    }
}

const deleteAllFromCart = async () => {
    try {
        let response = await fetch(`${getApiPrefix()}cart/all`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + auth.isAuthenticated()["accessToken"],
            },
            credentials: 'include'
        })
        return await response.json()
    } catch (err) {
        console.log(err)
        throw err
    }
}

const createOrder = async (productIds: Array<number>) => {
    try {
        let response = await fetch(`${getApiPrefix()}orders`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + auth.isAuthenticated()["accessToken"],
            },
            credentials: 'include',
            body: JSON.stringify({ goodsId: productIds })
        })
        return await response.json()
    } catch (err) {
        console.log(err)
        throw err
    }
}

const getImageUrlById = (id: number) => `${getApiPrefix()}image/show?imageId=${id}`;

export {
    getGoods,
    searchGoods,
    addAddress,
    removeAddress,
    updateUserInfo,
    getOrders,
    getCategoryList,
    getImageUrlById,
    getCart,
    addToCart,
    getProductInfo,
    deleteFromCart,
    createOrder,
    deleteAllFromCart,
    cancelOrder
}