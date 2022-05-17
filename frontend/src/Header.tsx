import './Header.css'

import {Nav, Navbar} from "react-bootstrap";
import React from "react";

export default function Header() {
    return <Navbar>
        <Navbar.Brand>Bildungsportal</Navbar.Brand>
        <Nav>
            <Nav.Link href="/">Bildungsangebote</Nav.Link>
            <Nav.Link href="/assessments">Prüfungseinsicht</Nav.Link>
        </Nav>
    </Navbar>
}