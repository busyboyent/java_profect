import React, {Component} from 'react';
import './Header.css'
import {NavLink} from "react-router-dom";
import SearchInput from "./SearchInput";
import auth from '../Auth/auth';

class Home extends Component {
    render() {
        return (
            <div className='header'>
                <div className='header-content'>
                    <NavLink to={"/"} exact activeClassName="header-active-element"
                             className="header-element">Товары</NavLink>
                    <NavLink to={"/user"} exact activeClassName="header-active-element" className="header-element">Личный
                        кабинет</NavLink>
                    {auth.isAuthenticated() ?
                        <React.Fragment>
                            <NavLink to={"/cart"} exact activeClassName="header-active-element"
                                     className="header-element">Корзина</NavLink>
                            <NavLink onClick={() => localStorage.removeItem('jwt')} to={"/auth"} exact
                                     activeClassName="header-active-element" className="header-element">Выйти</NavLink>
                        </React.Fragment>
                        : ""}
                    <SearchInput/>
                </div>
            </div>
        );
    }
}

export default Home;
