import React, {Component} from 'react';
import './Home.css'
import UserProfile from "../Components/UserProfile";
import HomeLayout from "./HomeLayout";
import UserPageMenu from "../Components/Menus/UserPageMenu";

class UserPage extends Component {
    render() {
        return (
            <HomeLayout
                pageContent={ <UserProfile /> }
                pageCategoryContent={ <UserPageMenu /> }
            />
        );
    }
}

export default UserPage;
