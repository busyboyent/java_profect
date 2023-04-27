import React, {Component} from 'react';
import "./Goods.css"
import {Link, withRouter} from "react-router-dom";
import Loader from "./Loader";
import {getGoods, getImageUrlById} from "../API/api";
import ItemModal from "./ItemModal";

// eslint-disable-next-line @typescript-eslint/no-unused-vars
interface IGoodsProps {
    showItemModal?: boolean
}

interface IGoodsState {
    isLoading: boolean,
    products: Array<Product>,
    error: string
}

export class Product {
    Id: number;
    description: string;
    name: string;
    category: string;
    price: number;
    discount: number;

    constructor(Id: number, name: string, description: string, category: string, price: number, discount: number) {
        this.Id = Id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.discount = discount;
    }
}

class Goods extends Component<any, IGoodsState> {
    constructor(props: any) {
        super(props);
        this.state = {
            isLoading: true,
            products: [],
            error: ""
        };
    }

    componentDidUpdate(prevProps: any) {
        if (this.props.match.params.category !== prevProps.match.params.category) {
            this.loadGoods(this.props.match.params.category);
        }
    }

    componentDidMount(): void {
        this.loadGoods()
    }

    loadGoods(category?: string): void {
        getGoods(category).then((response: any) => {
            if (!response) {
                this.setState({isLoading: false, error: "Не удалось загрузить список:  неизвестная ошибка"});
                return;
            }
            if (response.error) {
                this.setState({
                    isLoading: false,
                    error: `Не удалось загрузить список: ${response.status} ${response.path} ${response.error}`
                });
                return;
            }
            this.setState({
                isLoading: false,
                products: (response.items as Array<Product>),
            })
            if (this.state.products.length === 0) this.setState({error: "Нет продуктов!"})
        })
            .catch(err => this.setState({
                isLoading: false,
                error: "Не удалось загрузить список: " + err
            }))
    }

    renderList(): JSX.Element {
        return <React.Fragment>
            {this.props.showItemModal ? <ItemModal products={this.state.products}/> : ""}
            {this.state.error === "" ? this.state.products.map((item) => {
                return <div className='card'><Link key={item.Id} to={`/item/${item.Id}`}>
                    <img width="100px" src={getImageUrlById(item.Id)} alt={""}/> <h3>{item.name}</h3>
                    {item.price}.00 ₽
                </Link></div>;
            }) : <div className={'error-message'}>{this.state.error}</div>
            }
        </React.Fragment>;
    }

    render() {
        return (
            <div className='goods'>
                {this.state.isLoading ?
                    <div className="loader-box"><Loader/> <br/> Загружаем...</div>
                    : this.renderList()}
            </div>
        );
    }
}

export default withRouter(Goods);
