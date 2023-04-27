import React, {Component} from 'react';
import './Menu.css'
import {Link} from "react-router-dom";

class UserPageMenu extends Component {
    render() {
        return (
            <React.Fragment>
                <Link to={"/user"}>
                    Мой профиль</Link> <br/>
                <Link to={"/user/orders"}>Заказы</Link>
            </React.Fragment>
        );
    }
}

export default UserPageMenu;
