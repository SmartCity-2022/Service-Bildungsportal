
import 'bootstrap/dist/css/bootstrap.min.css'
import 'material-colors/dist/colors.var.css'

import './common.css';
import './index.css';

import React from 'react';
import ReactDOM from 'react-dom/client';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {Cookies} from "react-cookie"
import {isExpired} from "react-jwt";

import Header from "./Header";
import Overview from './Overview';
import EducationDetails from "./EducationDetails";
import InstitutionDetails from "./InstitutionDetails";

import {Configuration} from "./api";
import axios from "axios";
import {Toaster} from "react-hot-toast";
import AssessmentOverview from "./AssessmentOverview";
import Resources from "./Resources";

const getAccessToken = async () => {
    const cookieAccessToken = "accessToken"
    const cookies = new Cookies()
    let accessToken = cookies.get(cookieAccessToken)

    if (accessToken != null && isExpired(accessToken)) {
        const refreshToken = cookies.get("refreshToken")
        const backendUrl = process.env.REACT_APP_BACKEND

        await axios.post(`${backendUrl}/token`, {token: refreshToken})
            .then((res) => {
                accessToken = res.data.accessToken
                cookies.set(cookieAccessToken, accessToken)
            })
            .catch(console.error)
    }

    return accessToken
}

const config = new Configuration({
    basePath: process.env.REACT_APP_BACKEND,
    accessToken: getAccessToken()
})

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);
root.render(
    <React.StrictMode>
        <Toaster/>
        <Header config={config}/>
        <BrowserRouter>
            <Routes>
                <Route path={Resources.educationOverview()} element={<Overview config={config}/>}/>
                <Route path={Resources.educationDetails(':id')} element={<EducationDetails config={config}/>}/>
                <Route path={Resources.institutionDetails(':id')} element={<InstitutionDetails config={config}/>}/>
                <Route path={Resources.assessmentOverview()} element={<AssessmentOverview config={config}/>}/>
            </Routes>
        </BrowserRouter>
    </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
