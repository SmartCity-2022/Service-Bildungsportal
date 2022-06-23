import './Header.css'

import {Nav, Navbar} from "react-bootstrap";
import React from "react";
import {Configuration, User, UserApi} from "./api";
import Resources from "./Resources";

interface Props {
    config: Configuration
}

interface State {
    me: User | null
}

interface Link {
    href: string,
    caption: string
}

export default class Header extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state ={
            me: null
        }
    }

    async componentDidMount() {
        const userApi = new UserApi(this.props.config)
        this.setState({
            me: await userApi.me().then((res) => res.data).catch((_) => null)
        })
    }

    render() {
        const links = new Array<Link>()
        links.push({href: Resources.educationOverview(), caption: 'Bildungsangebote'})

        if (this.state.me) {
            if (this.state.me.student) {
                links.push({href: Resources.assessmentOverview(), caption: 'Prüfungseinsicht'})
            } else {
                links.push({href: Resources.registerStudent(), caption: 'Registrierung'})
            }

            if (this.state.me.institution) {
                links.push({href: Resources.educationCreation(), caption: 'Bildungsangebot erstellen'})
            }
        }

        return <Navbar>
            <Navbar.Brand>Bildungsportal</Navbar.Brand>
            <Nav>
                {links.map((l) => <Nav.Link href={`${process.env.PUBLIC_URL}/${l.href}`}>{l.caption}</Nav.Link>)}
            </Nav>
        </Navbar>
    }
}