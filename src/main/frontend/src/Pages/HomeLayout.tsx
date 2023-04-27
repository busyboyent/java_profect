import React, { Component } from 'react';
import Header from '../Components/Header'
import './Home.css'

interface IHomeProps{
    pageContent: JSX.Element,
    pageCategoryContent: JSX.Element
}

class HomeLayout extends Component<IHomeProps, { }> {
    render() {
        return (
            <React.Fragment>
                <div className='container'>
                    <div className="logo">
                        <div className="logo">Еда</div>
                    </div>
                    <Header />
                    <div className="category">
                        {this.props.pageCategoryContent}
                    </div>
                    <div className="main">
                        { this.props.pageContent }
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default HomeLayout;
