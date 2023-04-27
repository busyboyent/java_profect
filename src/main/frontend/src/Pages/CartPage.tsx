import React, {Component} from 'react';
import './Auth.css'
import HomeLayout from "./HomeLayout";
import {createOrder, deleteAllFromCart, deleteFromCart, getCart, getProductInfo} from "../API/api";
import {Product} from "../Components/Goods";

interface ICartPageState {
    cart: Array<Product>
}

class CartPage extends Component<any, ICartPageState> {
    constructor(props: any) {
        super(props);
        this.state = {
            cart: []
        }
    }

    componentDidMount(): void {
        this.loadItems()
    }

    loadItems() {
        this.setState({cart: []})
        getCart().then((response) => {
            response.items.map((productId: number) => {
                getProductInfo(productId.toString()).then((response) => {
                    this.setState({cart: this.state.cart.concat(response.items[0] as Product)});
                })
            })
        })
    }

    deleteItem(id: number) {
        deleteFromCart(id).then((response) => {
            alert("Удалено")
            this.loadItems()
        })
    }

    createOrder() {
        createOrder(this.state.cart.map(x => x.Id))
            .then((response1) => {
                deleteAllFromCart().then((response2) => {
                    this.setState({cart: []})
                    if(response1.status === "error")
                        alert(response1.message)
                    else
                        alert("Заказ создан")
                })
            })
    }

    getContent() {
        return <React.Fragment>
            <h1>Корзина</h1>
            {this.state.cart.map((product: Product) => {
                return <div key={product.Id}>{product.name} {product.price} ₽ <a
                    onClick={() => this.deleteItem(product.Id)}>Удалить</a></div>;
            })}
            <input onClick={() => this.createOrder()} type="submit" value="Оформить"/>
        </React.Fragment>
    }

    render() {
        return (
            <React.Fragment>
                <HomeLayout
                    pageContent={this.getContent()}
                    pageCategoryContent={<div></div>}/>
            </React.Fragment>
        );
    }
}

export default CartPage;
