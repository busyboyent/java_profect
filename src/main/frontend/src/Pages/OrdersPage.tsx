import React, {Component} from 'react';
import './Home.css'
import HomeLayout from "./HomeLayout";
import UserPageMenu from "../Components/Menus/UserPageMenu";
import {getOrders} from "../API/api";
import {cancelOrder} from "../API/api";

interface IOrdersPageState {
    orders: Array<Order>
}

class Order {
    id: number;
    date: number;
    title: string;
    status: string;

    constructor(id: number, date: number, title: string, status: string) {
        this.id = id;
        this.date = date;
        this.title = title
        this.status = status;
    }
}

class OrdersPage extends Component<any, IOrdersPageState> {
    constructor(props: any) {
        super(props);

        this.state = {
            orders: []
        };
    }

    componentDidMount(): void {
        this.updateOrdersList()
    }

    updateOrdersList(){
        const getOrderStatusFormatted = (status: string) => {
            switch (status) {
                case "InProgress":
                    return "В процессе";
                case "Delivered":
                    return "Доставлен";
                case "Cancelled":
                    return "Отменен";
                default:
                    return "Неизвестно"
            }
        }
        const getTitleFormatted = (products: any) => {
            return products.map((x: any) => `${x.name}  ${x.price}₽`).join(", ")
        }
        getOrders().then((response: any) => {
            this.setState({orders: response.map((x: any) => new Order(x.Id, x.creationDateTime, getTitleFormatted(x.goods), getOrderStatusFormatted(x.status)))});
        })
    }

    getOrderStatusClass(orderStatus: string) {
        switch (orderStatus) {
            case "Доставлен":
                return "order-status-success";
            case "В процессе":
                return "order-status-processing"
            case "Отменен":
                return "order-status-cancelled"
        }
        return "";
    }

    cancelOrder() {
        cancelOrder().then((response)=>{
            alert(JSON.stringify(response));
            this.updateOrdersList()
        })
    }

    ordersList(): JSX.Element {
        return (<div className={"orders-list"}>
            {this.state.orders.map((order) => {
                return <div key={order.id} className={"orders-list-item"}>
                    <div>
                        <h1>Заказ #{order.id}</h1>
                        <h5>{new Date(order.date).toLocaleString("ru-RU")}</h5>
                        <p>{order.title}</p>
                    </div>
                    <div><p className={"order-status " + this.getOrderStatusClass(order.status)}>{order.status}</p>
                        {order.status === "В процессе" ?
                            <a onClick={() => this.cancelOrder()}>Отменить</a> : ""}
                    </div>
                </div>;
            })}
        </div>)
    }

    render() {
        return (
            <HomeLayout
                pageContent={<React.Fragment>{this.state.orders.length !== 0 ? this.ordersList() :
                    <div><br/>Нет заказов</div>}</React.Fragment>}
                pageCategoryContent={<UserPageMenu/>}
            />
        );
    }
}

export default OrdersPage;
