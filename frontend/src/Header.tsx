import './Header.css'

import {Nav, Navbar} from "react-bootstrap";
import React from "react";

export default function Header() {
    return <Navbar>
        <Navbar.Brand>Bildungsportal</Navbar.Brand>
        <Nav>
            <Nav.Link href={`${process.env.PUBLIC_URL}/`}>Bildungsangebote</Nav.Link>
            <Nav.Link href={`${process.env.PUBLIC_URL}/assessment`}>Prüfungseinsicht</Nav.Link>
        </Nav>
    </Navbar>
}