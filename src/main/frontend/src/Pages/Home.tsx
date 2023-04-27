import React, {Component} from 'react';
import './Home.css'
import Goods from "../Components/Goods";
import HomeLayout from "./HomeLayout";
import Loader from "../Components/Loader";
import {getCategoryList} from "../API/api";
import {NavLink} from "react-router-dom";

// eslint-disable-next-line @typescript-eslint/no-unused-vars
interface IHomeProps {
    showItemModal?: boolean
}

interface IHomeState {
    categories: Array<string>
}

class Home extends Component<any, IHomeState> {
    constructor(props: any) {
        super(props);
        this.state = {categories: []}
    }

    componentDidMount(): void {
        getCategoryList().then((response: any) => {
            this.setState({categories: response.items})
        })
        .catch(err => console.log(err))
    }

    render() {
        return (
            <HomeLayout
                pageContent={<Goods {...this.props} showItemModal={this.props.showItemModal}/>}
                pageCategoryContent={<React.Fragment>
                    {this.state.categories ? <React.Fragment>
                        <h3>Категории</h3>
                        <NavLink exact to={"/"}>Все</NavLink>
                        {this.state.categories.map(x =>
                            <div key={x}><NavLink to={`/category/${x}`}>{x}</NavLink></div>)}</React.Fragment> : <Loader/>}
                </React.Fragment>}
            />
        );
    }
}

export default Home;
