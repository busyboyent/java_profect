import React, {Component} from 'react';
import './Header.css'
import {searchGoods} from "../API/api";
import { Product } from "./Goods";
import {Link} from "react-router-dom";
import _ from 'lodash';

interface ISearchInputState {
    items: Array<Product>,
    visible: boolean,
    searchRequest: string
}

class SearchInput extends Component<any, ISearchInputState> {
    constructor(props: any) {
        super(props);
        this.state = {items: [], visible: false, searchRequest: ""};
    }

    loadItems() {
        if(this.state.searchRequest.trim().length === 0) return;
        searchGoods(this.state.searchRequest).then((response) => {
            console.log(response.items)
            this.setState({items: response.items})
            if(response.items.length === 0){
                this.setState({
                    items: [new Product(0,"🚩 Ничего не найдено!", "Ошибка", "", 0, 0)]
                })
            }
        })
        .catch(err => console.log(err))
    }

    handleChange() {
        return (event: any) => {
            this.setState({searchRequest: event.target.value});
            this.loadItems();
        };
    }

    render() {
        return (
            <div className={"search"}>
                <input onChange={_.debounce(this.handleChange(), 250)} type="text" onFocus={() => this.setState({visible: true})}
                       onBlur={() => setTimeout(() => this.setState({visible: false}), 250)} placeholder="🔍 Поиск"/>
                {this.state.visible ?
                    <div className={"search-items"}>
                        {this.state.items.map((item) => {
                            return <Link to={`/item/${item.Id}`}>
                                <div key={item.Id} className={"search-item"}>
                                    {item.name}
                                </div>
                            </Link>;
                        })}
                    </div>
                    : ""}
            </div>
        );
    }
}

export default SearchInput;
