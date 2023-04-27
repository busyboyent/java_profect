import React, {Component} from 'react';
import '../Pages/Home.css'
import {Link, withRouter} from "react-router-dom";
import {Product} from "./Goods";
import {addToCart, getImageUrlById} from "../API/api";

class ItemModal extends Component<any, any> {
    addToCart(Id: number){
        addToCart(Id).then((response)=>{
            alert("Добавлено")
            this.props.history.push("/")
        })
        .catch(err=> alert(err));
    }

    render() {
        const getCurrentProduct: () => Product = () =>
            this.props.products.filter((x: Product) => x.Id === parseInt(this.props.match.params.id))[0];

        return (
            <React.Fragment>
                <div className="modal">
                    <div className="modal-content">
                        <Link to="/" title="Close" className="close">×</Link>
                        <div className="modal-body">
                            <img width="150px" src={getImageUrlById(getCurrentProduct().Id)} alt={""}/>

                            <h3 className="modal-title">{getCurrentProduct().name}</h3>
                            <p>{getCurrentProduct().description}</p>
                            <p>{getCurrentProduct().price - getCurrentProduct().discount} ₽ (скидка {getCurrentProduct().discount} ₽)</p>
                            <input type={"button"} onClick={()=>this.addToCart(getCurrentProduct().Id)} className="blueButton" value={"Добавить в корзину"}/>
                        </div>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default withRouter(ItemModal);
