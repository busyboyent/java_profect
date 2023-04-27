import React, {Component} from 'react';
import {getProfile} from '../Auth/auth-api'
import {removeAddress} from '../API/api'
import Loader from "./Loader";
import EditUserProfileWindow from "./EditUserProfileWindow";
import auth from "../Auth/auth";

interface IUserProfileState {
    userProfile: any;
    addresses: any;
    showEditWindow: boolean,
    error: string
}

class UserProfile extends Component<any, IUserProfileState> {
    constructor(props: any) {
        super(props);
        this.state = {
            userProfile: null,
            addresses: [],
            showEditWindow: false,
            error: ""
        }
    }

    componentDidMount(): void {
        this.loadProfile()
    }

    loadProfile() {
        this.setState({userProfile: null})
        getProfile().then((result) => {
                if (result["username"]) {
                    this.setState({
                        userProfile: result,
                        addresses: result["addresses"]
                    });
                }
                else{
                    auth.clearJWT(() => console.log("Cleared auth"))
                    this.setState({error: JSON.stringify(result)})
                }
            }
        ).catch((err)=> {
            this.setState({error: JSON.stringify(err)})
        })
    }


    deleteAddress(id: number) {
        const newAddresses = this.state.addresses.filter((x: any) => {
            return x.id !== id
        });

        removeAddress(id).then(x => {
            this.setState({
                addresses: newAddresses
            })
        })
    }

    getAddressDeleteButton(id: number) {
        // eslint-disable-next-line jsx-a11y/anchor-is-valid
        return <u><a onClick={() => this.deleteAddress(id)}>Удалить</a></u>
    }

    renderUserInfo() {
        return <React.Fragment>
            Имя: {this.state.userProfile["username"]} < br/>
            <h3>Сохраненные адреса</h3>
            <ul>
                {this.state["addresses"].map((x: any) =>
                    <li key={x.id}>{x.id} {x.country} {x.city} {x.locationAddress} {x.postalCode} {this.getAddressDeleteButton(x.id)}</li>)}
            </ul>
            <input type="button" className="blueButton" value="Изменить информацию"
                   onClick={() => this.setState({showEditWindow: true})}/>
        </React.Fragment>
    }

    render() {
        return (
            <React.Fragment>
                {this.state.showEditWindow ? <EditUserProfileWindow userProfile={this.state.userProfile}
                                                                    closeButtonCallback={() => this.setState({showEditWindow: false})}
                                                                    reloadProfileCallback={() => this.loadProfile()} /> : ""}
                <div className={"userProfile"}>
                    {this.state.error !== "" ? <div className={'error-message'}>{this.state.error}</div>
                         : this.state.userProfile === null ? <Loader/> : this.renderUserInfo()}
                </div>
            </React.Fragment>
        );
    }
}

export default UserProfile;
