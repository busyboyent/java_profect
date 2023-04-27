import React, {Component} from 'react';
import {HashRouter as Router, Route, Switch} from 'react-router-dom';
import './App.css';
import Home from "./Pages/Home";
import UserPage from "./Pages/UserPage";
import Auth from "./Pages/Auth";
import PrivateRoute from "./Auth/PrivateRoute";
import OrdersPage from "./Pages/OrdersPage";
import CartPage from "./Pages/CartPage";

class App extends Component {
  render() {
    return (
        <React.Fragment>
          <Router>
            <Switch>
                <Route exact path="/" render={(props) => <Home {...props} />} />
                <Route exact path="/cart" component={CartPage} />
                <Route exact path="/category/:category" render={(props) => <Home {...props} />} />
                <Route exact path="/item/:id" render={(props) => <Home {...props} showItemModal={true}/>} />
                <PrivateRoute exact path="/user" component={UserPage} />
                <PrivateRoute exact path="/user/orders" component={OrdersPage} />
                <Route exact path="/auth" component={Auth} />
            </Switch>
          </Router>
        </React.Fragment>
    );
  }
}

export default App;
